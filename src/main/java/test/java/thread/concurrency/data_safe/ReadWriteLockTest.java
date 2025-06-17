package test.java.thread.concurrency.data_safe;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// run: Counter = 10000
//Completed 100*100 actions in 389 ms
// https://www.cnblogs.com/java-note/p/18816268
// 可以实现线程安全
public class ReadWriteLockTest {
    static int count = 0;
    // 默认为非公平锁 ： 多个线程时线程不会按顺序执行
    private static final ReadWriteLock lock = new ReentrantReadWriteLock(false);
    // 公平锁 ： 多个线程时线程会按执行顺序
//    private static final ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        long start = System.currentTimeMillis();
//        int n = 5;
        int n = 100;
        int k = 100;
//        int k = 5;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                int finalI = i;
                int finalJ = j;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        lock.writeLock().lock();
                        try {
                            count++;
                            if (finalI == n && finalJ == k) {
                                long end = System.currentTimeMillis();
                                long time = end - start;
                                System.err.println("run: " + "Counter = " + count);
                                System.err.println("Completed " + finalI + "*" + finalJ + " actions in " + time + " ms");
                            }
                            System.err.println("thread name:" + Thread.currentThread().getName() + "，开始执行！");
                        } finally {
//                        Log.e(TAG, "finalI = " + finalI + ",finalK = " + finalJ);
//                        Log.e(TAG, "run: " + "Counter = " + test.count);
                            lock.writeLock().unlock();
                        }
                    }
                }).start();
            }
        }
    }
}
