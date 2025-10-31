package test.java.thread.concurrency.data_safe;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class ConcurrentHashMapTest {
    private static final ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        long start = System.currentTimeMillis();
//        int n = 5;
        int n = 100;
        int k = 100;
        data.put("test", 0);
//        int k = 5;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                int finalI = i;
                int finalJ = j;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data.put("test", data.get("test") + 1);
                        if (finalI == n && finalJ == k) {
                            long end = System.currentTimeMillis();
                            long time = end - start;
                            System.err.println("run: " + "Counter = " + data.get("test"));
                            System.err.println("Completed " + finalI + "*" + finalJ + " actions in " + time + " ms");
                        }
                        System.err.println("thread name:" + Thread.currentThread().getName() + "，开始执行！");

                    }
                }).start();
            }
        }
    }
}
