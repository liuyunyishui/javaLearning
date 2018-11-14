package liuyun.program.salary;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MyHeap<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private PriorityQueue<T> maxHeap;

    public MyHeap(Comparator c) {
        maxHeap = new PriorityQueue<T>(DEFAULT_INITIAL_CAPACITY, c);
    }

    public T poll() {
        return maxHeap.poll();
    }

    public boolean put(T o) {
        return maxHeap.offer(o);
    }

    public List<T> sortList(){
        return null;
    };
}
