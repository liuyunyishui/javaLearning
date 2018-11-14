package liuyun.program.salary;

import java.io.*;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SalaryMain {

    final static PriorityQueue<SalarySum> HEAP = new PriorityQueue<>((s1, s2) -> s1.getSum() - s2.getSum());
    private final static int SCORED = 10000000;
    private final static String FILE_PATH = "E:\\";
    private ExecutorService executors = Executors.newFixedThreadPool(8);
    private AtomicInteger writeCount = new AtomicInteger(SCORED);
    private BlockingQueue<Salary> writeSalaryQueue = new LinkedBlockingQueue<>(2<<20);
    private BlockingQueue<Salary> readSalaryQueue = new LinkedBlockingQueue<>(2<<20);
    private AtomicInteger readCount = new AtomicInteger(SCORED);

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        SalaryMain main = new SalaryMain();
        main.start();

        long end = System.currentTimeMillis();
        System.out.println("use time :" + (end - start));
//        13743ms
//        8828ms
//        on myself computer is{40567,35449,32794,39661,36431}

        while (true) {
            main.exit();
        }
    }

    public static void saveResult() {
        File resultFile = new File(FILE_PATH + "result.txt");
        Writer writer = null;
        try {
            boolean flag = resultFile.delete();
            flag = resultFile.createNewFile();
            writer = new FileWriter(resultFile, true);
            System.out.println("heap size:" + HEAP.size());
            for (int i = 10; i > 0; i--) {
                SalarySum sum = HEAP.poll();
                System.out.println("" + i + sum.toString());
                writer.write(sum.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void start() {
        // TODO:  use properties to config
        File file = new File(FILE_PATH + "salary.txt");
        boolean flag = file.delete();
        try {
            flag = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        productSalary();
        // todo : use NIO/AIO
        //bug : There is some problem with reader executor; sometimes it's work wrong.
        //Buffer.readLine() work not well;
        writeSalary(file);
        readSalary(file);
        sumSalary();

        //todo : rebuild use Semaphore or CyclicBarrier
        //wait to statistics
        try {
            int i = 0;
            while (true) {
                if (isWorkComplete()) {
                    break;
                } else {
                    logWorkStatus();
                    System.out.println("active thread count: " + Thread.activeCount());
                    Thread.sleep(500L);
                    System.out.println("wait :" + (i++) + "times");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // the heap use top n         done
        // /or use quickSort to select top n
        SalaryStatistics.statistics(HEAP);

        saveResult();
        logWorkStatus();
    }

    private void logWorkStatus() {
        System.out.println("writeCount:" + writeCount + ", write queue size:" + writeSalaryQueue.size() +
                ", readCount:" + readCount + ", read queue size:" + readSalaryQueue.size());
    }

    private void productSalary() {
        //the Salary Producer executors start thread
        new Thread(() -> {
            int threadLimit = 2;
            while (threadLimit > 0 && writeCount.get() > 0) {
                executors.execute(new SalaryProducer(writeCount, writeSalaryQueue));
                threadLimit--;
            }
        }).start();
    }

    private void writeSalary(File file) {
        //write executors start thread
        new Thread(() -> {
            int threadLimit = 2;
            try {
                do {
                    executors.execute(new SalaryWriter(file, writeSalaryQueue, writeCount));
                    threadLimit--;
                } while (threadLimit > 0 && !writeSalaryQueue.isEmpty());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void readSalary(File file) {
        //read executors start thread
        new Thread(() -> {
            int threadLimit = 2;
            do {
                try {
                    executors.execute(new SalaryReader(file, readSalaryQueue));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                threadLimit--;
            } while (threadLimit > 0);
        }).start();
    }

    private void sumSalary() {
        //Statistics start
        new Thread(() -> {
            int threadLimit = 4;
            while (threadLimit > 0 && readCount.get() > 0) {
                executors.execute(new SalaryStatistics(readCount, readSalaryQueue));
                threadLimit--;
            }
        }).start();
    }

    private boolean isWorkComplete() {
        return writeCount.get() < 0 && readCount.get() < 0 &&
                writeSalaryQueue.isEmpty() && readSalaryQueue.isEmpty();
    }

    public void exit() {
        if (isWorkComplete()) {
            SalaryWriter.close();
            SalaryReader.close();
            System.exit(0);
        }
    }
}
