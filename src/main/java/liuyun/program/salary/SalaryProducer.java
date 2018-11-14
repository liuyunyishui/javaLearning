package liuyun.program.salary;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SalaryProducer implements Runnable {

    private static int DEF_NAME_LEN = 4;
    private static int DEF_SALARY_MIN = 0;
    private static int DEF_SALARY_MAX = 100;
    private static int DEF_BONUS_MIN = 0;
    private static int DEF_BONUS_MAX = 5;

    private int nameLen = DEF_NAME_LEN;
    private int salaryMin = DEF_SALARY_MIN;
    private int salaryMax = DEF_SALARY_MAX;
    private int bonusMin = DEF_BONUS_MIN;
    private int bonusMax = DEF_BONUS_MAX;

    private AtomicInteger count;
    private BlockingQueue<Salary> salaryQueue;

    private int threadId = new Random().nextInt(100);

    public SalaryProducer() {
    }

    public SalaryProducer(AtomicInteger count, BlockingQueue<Salary> salaryQueue) {
        this.count = count;
        this.salaryQueue = salaryQueue;
    }
    public SalaryProducer(AtomicInteger count, BlockingQueue<Salary> salaryQueue, int salaryMin, int salaryMax, int bonusMin, int bonusMax, int nameLen) {
        this.count = count;
        this.salaryQueue = salaryQueue;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.bonusMin = bonusMin;
        this.bonusMax = bonusMax;
        this.nameLen = nameLen;
    }

    public static Salary getRandomSalary(int nameLen, int salaryMin, int salaryMax, int bonusMin, int bonusMax) {
        if (nameLen <= 0) {
            throw new IllegalArgumentException("There is wrong name length!");
        }
        if (salaryMin < 0 || salaryMax < salaryMin) {
            throw new IllegalArgumentException("The salary scope is wrong!");
        }
        if (bonusMin < 0 || bonusMax < bonusMin) {
            throw new IllegalArgumentException("The bonus scope is wrong!");
        }

        Random random = new Random();
        String name = "";
        for (int i = nameLen; i > 0; i--) {
            name += Salary.BASE_CHAR[random.nextInt(Salary.BASE_CHAR.length)];
        }
        int salary = salaryMin + random.nextInt(salaryMax - salaryMin);

        int bonus = bonusMin + random.nextInt(bonusMax - bonusMin);
        return new Salary(name, salary, bonus);
    }

    public Salary getRandomSalary() {
        return getRandomSalary(nameLen, salaryMin, salaryMax, bonusMin, bonusMax);
    }

    public String getThreadName() {
        return "SalaryProducer Thread " + threadId;
    }

    @Override
    public void run() {
        try {
            while (count.get() >= 0) {
                if (count.decrementAndGet() >= 0) {
                    salaryQueue.put(getRandomSalary());
//                    System.out.println(count + " is wait for write!");
                }
            }
        } catch (InterruptedException e) {
//            count.getAndIncrement();
            Thread.currentThread().interrupt();
        }
    }


}
