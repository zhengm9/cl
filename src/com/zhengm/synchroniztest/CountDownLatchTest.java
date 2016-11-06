package com.zhengm.synchroniztest;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengming on 16/11/6.
 */
public class CountDownLatchTest {
    private CountDownLatch clStart = new CountDownLatch(1);
    private CountDownLatch clEnd = new CountDownLatch(10);

    @Test
    public void test() throws InterruptedException {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        for(int i=0;i<10;i++){
            listeningExecutorService.submit(
                    new CountDownLatchEnt(clStart, clEnd)
            );
        }
        System.out.println("new thread submitted...");
        Thread.sleep(3000L);
        clStart.countDown();
        if(clEnd.await(2000, TimeUnit.MILLISECONDS)) {
            System.out.println("new thread finished!");
        }else {
            System.out.println("i can not wait for all finished");
        }
    }
}
