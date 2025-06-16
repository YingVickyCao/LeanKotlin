package test.java.thread.create_thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThread3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // https://cloud.tencent.com/developer/article/2528687
        // 如何创建线程？ 实现 Runnable 接口

        FutureTask<Integer> future= new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 123;
            }
        });
        new Thread(future).start();

        // 123
        System.out.println(future.get());
    }
}
