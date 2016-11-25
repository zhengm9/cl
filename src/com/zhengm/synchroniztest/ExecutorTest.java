package com.zhengm.synchroniztest;

import com.google.common.util.concurrent.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by zhengming on 16/11/25.
 */
public class ExecutorTest {

    public List<String> testExecutor()
    {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        List<ListenableFuture<String>> futureList = new ArrayList<ListenableFuture<String>>();
        String[] strings = {"src1","src2","src3"};
        for(final String str : strings)
        {
            ListenableFuture<String> future = executorService.submit(new Callable<String>() {
                public String call() throws Exception {
                    return new ViceExecutorTest().testExecutor(str);
                }
            });
            futureList.add(future);
        }

        final CountDownLatch latch = new CountDownLatch(1);
        ListenableFuture<List<String>> listenfutureList = Futures.allAsList(futureList);
        final List<String> stringList = new ArrayList<String>();
        Futures.addCallback(listenfutureList, new FutureCallback<List<String>>() {


            public void onSuccess(List<String> strings) {
                for(String str : strings)
                {
                    stringList.add(str);
                }
                latch.countDown();
            }

            public void onFailure(Throwable throwable) {
                System.out.println(throwable.getMessage());
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return stringList;
    }

    public static void main(String[] args)
    {
        System.out.println(        new ExecutorTest().testExecutor().size());
    }

}


