package liuyun.program.salary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class SalaryReader implements Runnable {
    private static final int CACHE_SIZE = 1 << 10;
    private static BufferedReader reader;
    private int threadId = new Random().nextInt(100);
    private BlockingQueue<Salary> queue;

    public SalaryReader(File file) throws FileNotFoundException {
        if (null == reader) {
            reader = new BufferedReader(new FileReader(file));
        }
    }

    public SalaryReader(File file, BlockingQueue<Salary> queue) throws FileNotFoundException {
        if (null == reader) {
            reader = new BufferedReader(new FileReader(file));
        }
        this.queue = queue;
    }

    public static List<Salary> readSalary(int record) {
        List list = new ArrayList(record);
        try {
            while (record-- > 0 && reader.ready()) {
                String tmp;
                synchronized (reader) {
                    tmp = reader.readLine();
                }
                if (null == tmp || "" == tmp) {
                    continue;
                }
                String[] salaryStrs = tmp.split(",");
                if (salaryStrs.length != 3) {
                    System.out.println(tmp);
                    continue;
                }
                String salaryName = salaryStrs[0];
                if (salaryName.length() < 2) {
                    System.out.println(tmp);
                    salaryName += "  ";
                }
                Salary salary = new Salary(salaryName, Integer.valueOf(salaryStrs[1]),
                        Integer.valueOf(salaryStrs[2]));
                list.add(salary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getThreadName() {
        return "SalaryReader Thread " + threadId;
    }

    @Override
    public void run() {
        while (true) {
            List salaries = readSalary(CACHE_SIZE);
            if (0 == salaries.size()) {
                Thread.currentThread().interrupt();
            }
            queue.addAll(salaries);
        }

    }
}
