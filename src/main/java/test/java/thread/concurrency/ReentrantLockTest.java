package test.java.thread.concurrency;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

// run: Counter = 10000
//Completed 100*100 actions in 326 ms
// 可以实现线程同步
// https://www.cnblogs.com/vipstone/p/15139226.html
public class ReentrantLockTest {
    static int count = 0;
    // 非公平锁
    private static final ReentrantLock lock = new ReentrantLock(false);
    // 公平锁
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
                        lock.lock();
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
                            if (lock.isLocked()) {
                                lock.unlock();
                            }
                        }
                    }
                }).start();
            }
        }
    }
}
