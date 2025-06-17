package test.java.thread.concurrency;

import java.util.concurrent.Exchanger;

public class ExchangerTest {

    public static void main(String[] args) {
        // 交换同步器
        Exchanger<String> exchanger = new Exchanger<>();

        // 线程1
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String value = "A";
                    System.out.println("thread name:" +  Thread.currentThread().getName() + " 原数据：" + value);
                    String newValue = exchanger.exchange(value);
                    System.out.println("thread name:" +  Thread.currentThread().getName() + " 交换后的数据：" + newValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 线程2
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String value = "B";
                    System.out.println("thread name:" +  Thread.currentThread().getName() + " 原数据：" + value);
                    String newValue = exchanger.exchange(value);
                    System.out.println("thread name:" +  Thread.currentThread().getName() + " 交换后的数据：" + newValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}