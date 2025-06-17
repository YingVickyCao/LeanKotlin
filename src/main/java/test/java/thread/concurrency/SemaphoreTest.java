package test.java.thread.concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 同一时刻仅允许最多3个线程获取许可
        final Semaphore semaphore = new Semaphore(3);
        // 初始化 5 个线程生成
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 如果超过了许可数量,其他线程将在此等待
                        semaphore.acquire();
                        System.out.println(format.format(new Date()) +  " thread name:" +  Thread.currentThread().getName() + " 获取许可，开始执行任务");
                        // 假设执行某项任务的耗时
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 使用完后释放许可
                        semaphore.release();
                    }
                }
            }).start();
        }
    }
}