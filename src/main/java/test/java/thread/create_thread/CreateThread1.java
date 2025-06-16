package test.java.thread.create_thread;


public class CreateThread1 {
    public static void main(String[] args) {
        new MyThread().start();
    }

    // 如何创建线程？ 方式 1：继承 Thread 类
    // https://cloud.tencent.com/developer/article/2528687
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread is running");
        }
    }
}
