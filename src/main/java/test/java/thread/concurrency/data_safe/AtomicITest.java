package test.java.thread.concurrency.data_safe;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

// run: Counter = 10000
//Completed 100*100 actions in 385 ms
// 多线程时线程安全
// https://www.cnblogs.com/java-note/p/18816268
public class AtomicITest {
    private static final AtomicInteger data = new AtomicInteger();

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
                        data.incrementAndGet();
                        if (finalI == n && finalJ == k) {
                            long end = System.currentTimeMillis();
                            long time = end - start;
                            System.err.println("run: " + "Counter = " + data.get());
                            System.err.println("Completed " + finalI + "*" + finalJ + " actions in " + time + " ms");
                        }
                        System.err.println("thread name:" + Thread.currentThread().getName() + "，开始执行！");

                    }
                }).start();
            }
        }
    }
}
