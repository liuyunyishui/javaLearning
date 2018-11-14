package liuyun.program.salary;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SalaryStatistics implements Runnable {
    private static ConcurrentHashMap<String, SalarySum> map = new ConcurrentHashMap(2 << 11);
    private int threadId = new Random().nextInt(100);
    private AtomicInteger count;
    private BlockingQueue<Salary> queue;

    public SalaryStatistics() {
    }

    public SalaryStatistics(AtomicInteger count, BlockingQueue<Salary> queue) {
        this.count = count;
        this.queue = queue;
    }

    public static PriorityQueue<SalarySum> statistics(PriorityQueue<SalarySum> heap) {
        return statistics(heap, 10);
    }

    public static PriorityQueue<SalarySum> statistics(PriorityQueue<SalarySum> heap, int top) {
//        heap.addAll(map.values());
        int i = 0;
        for (Map.Entry<String, SalarySum> e : map.entrySet()) {
            i++;
            SalarySum sum = e.getValue();
            SalarySum min = heap.peek();
            if (i <= top) {
                heap.offer(sum);
            } else if (sum.getSum() > min.getSum()) {
                heap.remove(min);
                heap.offer(sum);
            }
        }
        return heap;
    }

    public String getThreadName() {
        return "SalaryStatistics Thread " + threadId;
    }

    public void addSalary(Salary salary) {
        String salaryName = salary.getName();
        String statisticsName = salaryName.substring(0, 2);
        SalarySum oldSum = map.get(statisticsName);
        SalarySum newSum;
        boolean success = false;
        if (null == oldSum) {
            newSum = new SalarySum(salary);
            oldSum = map.putIfAbsent(statisticsName, newSum);
            if (null == oldSum) {
                success = true;
            }
        } else {
            newSum = oldSum.add(salary);
            success = map.replace(statisticsName, oldSum, newSum);
        }
//            map.put(statisticsName, salarySum);
        if (!success) {
            addSalary(salary);
        }
    }

    @Override
    public void run() {
        try {
            while (count.get() >= 0) {
                if (count.decrementAndGet() >= 0) {
                    addSalary(queue.take());
                }
            }
        } catch (InterruptedException e) {
//            count.getAndIncrement();
            Thread.currentThread().interrupt();
        }
    }
}
