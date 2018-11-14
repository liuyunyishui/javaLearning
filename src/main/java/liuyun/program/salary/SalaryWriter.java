package liuyun.program.salary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class SalaryWriter implements Runnable {
    private static final int CACHE_SIZE = 1 << 10;
    private static AtomicInteger threadCount = new AtomicInteger(0);
    private static FileWriter writer;
    private List<Salary> cache = new ArrayList<>(CACHE_SIZE);
    private BlockingQueue<Salary> queue;
    private int index = 0;
    private AtomicInteger count;

    {
        threadCount.incrementAndGet();
    }

    public SalaryWriter(File file, BlockingQueue<Salary> queue, AtomicInteger count) throws IOException {
        if (null == writer) {
            writer = new FileWriter(file, true);
        }
        this.queue = queue;
        this.count = count;
    }

    public SalaryWriter(File file) throws IOException {
        if (null == writer) {
            writer = new FileWriter(file, true);
        }
    }

    public static void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getThreadCount() {
        return "SalaryReader Thread " + threadCount;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Salary salary = queue.poll(500, TimeUnit.MILLISECONDS);

                if (null != salary) {
                    cache.add(salary);
                    index++;
                }
                if (index == (CACHE_SIZE - 1)) {
                    writeSalaries(cache);
                    index = 0;
                    cache.clear();
                }
                if (count.get() <= 0 && queue.size() == 0) {
                    writeSalaries(cache);
                    cache.clear();
                    index = 0;
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            writeSalaries(cache);
            cache.clear();
            index = 0;
        }
    }

    public void writeSalaries(List<Salary> salaries) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Salary salary : salaries) {
                if (null != salary) {
                    sb.append(salary).append("\n");
                }
            }
            writer.append(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
