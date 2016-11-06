package com.zhengm.synchroniztest;

import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by zhengming on 16/11/6.
 */
public class SynchronizTest {
    ListeningExecutorService listeningExecutorService = MoreExecutors.
            listeningDecorator(Executors.newCachedThreadPool());

    @Test
    public void test1() throws ExecutionException, InterruptedException {

        final SynchronizedClass1 c1 = new SynchronizedClass1();
        final SynchronizedClass1 c2 = new SynchronizedClass1();

        ListenableFuture<Integer> future = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                c1.test1();
                return 1;
            }
        });

        ListenableFuture<Integer> future2 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                c1.test2();
                return 2;
            }
        });
        future.get();
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        final SynchronizedClass2 c1 = new SynchronizedClass2();
        final SynchronizedClass2 c2 = new SynchronizedClass2();

        ListenableFuture<Integer> future = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                c1.test();
                return 1;
            }
        });

        ListenableFuture<Integer> future2 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                c2.test();
                return 2;
            }
        });
        future.get();
    }

    /*
     *测试同一对象中多个锁对象是否互相影响:不影响
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        final SynchronizedClass2 c1 = new SynchronizedClass2();

        ListenableFuture<Integer> future = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                c1.test2();
                return 1;
            }
        });

        ListenableFuture<Integer> future2 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                c1.test3();
                return 2;
            }
        });
        future.get();
        future2.get();

    }

    /*
    *测试静态变量锁的影响范围：类范围
    */
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        final SynchronizedClass2 c1 = new SynchronizedClass2();
        final SynchronizedClass2 c2 = new SynchronizedClass2();

        ListenableFuture future = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test4();
                return null;
            }
        });

        ListenableFuture future2 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c2.test4();
                return null;
            }
        });
        future.get();
        future2.get();
    }

    /*
    *测试类名锁的影响范围：类范围
    */
    @Test
    public void test5() throws ExecutionException, InterruptedException {
        final SynchronizedClass2 c1 = new SynchronizedClass2();
        final SynchronizedClass2 c2 = new SynchronizedClass2();

        ListenableFuture future = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test5();
                return null;
            }
        });

        ListenableFuture future2 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c2.test5();
                return null;
            }
        });

        ListenableFuture future3 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c2.test2();
                return null;
            }
        });
        future.get();
        future2.get();
    }

    /*
    *测试静态方法的影响范围：
    *  静态方法加锁不会影响同一类、同一对象的其他方法，只影响同一类的自身方法；
    * 非静态方法加锁影响同一对象中的所有加锁方法，不影响同一类其他对象的加锁方法。
    */
    @Test
    public void test6() throws ExecutionException, InterruptedException {
        final SynchronizedClass1 c1 = new SynchronizedClass1();
        final SynchronizedClass1 c2 = new SynchronizedClass1();

        ListenableFuture future = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test3();
                return null;
            }
        });

        ListenableFuture future2 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c2.test3();
                return null;
            }
        });

        ListenableFuture future3 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c2.test2();
                return null;
            }
        });

        ListenableFuture future4 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test2();
                return null;
            }
        });
        future.get();
        future2.get();
        future3.get();
        future4.get();

    }

    /*
    *测试方法锁与块锁的互相影响：不影响
    *
    */
    @Test
    public void test7() throws ExecutionException, InterruptedException {
        final SynchronizedClass2 c1 = new SynchronizedClass2();
        final SynchronizedClass2 c2 = new SynchronizedClass2();

        ListenableFuture future = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test6();
                return null;
            }
        });

        ListenableFuture future2 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test2();
                return null;
            }
        });

        ListenableFuture future3 = listeningExecutorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                c1.test7();
                return null;
            }
        });

        future.get();
        future2.get();
        future3.get();

    }
}
