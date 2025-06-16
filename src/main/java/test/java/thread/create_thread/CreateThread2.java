package test.java.thread.create_thread;

public class CreateThread2 {
    public static void main(String[] args) {
        // https://cloud.tencent.com/developer/article/2528687
        // 如何创建线程？ 实现 Runnable 接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程执行中...");
            }
        }).start();
    }
}
