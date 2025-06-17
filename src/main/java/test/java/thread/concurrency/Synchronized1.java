package test.java.thread.concurrency;

import java.io.IOException;

public class Synchronized1 {
    private static class Test {
        public int count = 0;

        public synchronized void increment() {
            count++;
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Test test = new Test();
        int n = 100;
        int k = 1000;
        for (; n >= 0; n--) {
            for (; k >= 0; k--) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        test.increment();
                        System.out.println("Counter = " + test.count);
                    }
                }).start();
            }
        }

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Counter = " + test.count);
        System.out.println("Completed " + n + "*" + k + " actions in " + time + " ms");
    }
}

