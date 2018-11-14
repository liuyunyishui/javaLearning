package liuyun.concurrcency;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ly on 2018/11/11.
 */
public class Indexer implements Runnable {

    private final static int N_CONSUMERS = 10;

    private final BlockingQueue queue;

    public Indexer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                queue.take();
            }
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public static void startIndexing(File[] roots) {
        BlockingQueue queue = new LinkedBlockingQueue();
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}
