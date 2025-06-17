package test.java.thread.concurrency;

import java.io.IOException;
import java.util.concurrent.*;

// run: Counter = 9901
//Completed 100*100 actions in 44 ms
// 只能实现并发，并不能实现线程同步
public class TestExecutor {
    static int count = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        ExecutorService pool = Executors.newCachedThreadPool();

//        Future<Integer> future = pool.submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                System.out.println("任务执行");
//                return 10;
//            }
//        });

        long start = System.currentTimeMillis();
//        int n = 5;
        int n = 100;
        int k = 100;
//        int k = 5;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                int finalI = i;
                int finalJ = j;

                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        count++;
//                                Log.e(TAG, "run: " + "thread name:" + Thread.currentThread().getName() + "，开始执行！");
//                        Log.e(TAG, "finalI = " + finalI + ",finalK = " + finalJ);
//                        Log.e(TAG, "run: " + "Counter = " + test.count);
                        if (finalI == n && finalJ == k) {
                            long end = System.currentTimeMillis();
                            long time = end - start;
                            System.err.println("run: " + "Counter = " + count);
                            System.err.println( "Completed " + finalI + "*" + finalJ + " actions in " + time + " ms");
                            pool.shutdown();
                        }
                    }
                });
            }
        }

        System.in.read();
    }
}
