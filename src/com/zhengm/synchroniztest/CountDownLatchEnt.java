package com.zhengm.synchroniztest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zhengming on 16/11/6.
 */
public class CountDownLatchEnt implements Runnable {
    private CountDownLatch clStart = null;
    private CountDownLatch clEnd = null;

    public CountDownLatchEnt(CountDownLatch clStart, CountDownLatch clEnd) {
        this.clStart = clStart;
        this.clEnd = clEnd;
    }

    public void run() {
        try {
            this.clStart.await();
            System.out.println("new thread start!");
            Thread.sleep(5000);
//            this.clEnd.await();
            this.clEnd.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
