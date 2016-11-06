//import java.util.concurrent.Executors;

import com.google.common.collect.ObjectArrays;
import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by zhengming on 16/11/6.
 */
public class GuavaThread {
    private static final int THREAD_NUM = 10;
    private volatile boolean threadIsSuc = false;
    private Object lock = new Object();

    @Test
    public void test() {

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREAD_NUM));
        ListenableFuture<Integer> future = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 10;
            }

        });

        Futures.addCallback(future, new FutureCallback<Integer>() {
            public void onSuccess(Integer integer) {
                synchronized (lock) {
                    threadIsSuc = true;
                    System.out.println("future success! int:" + integer);
                }
            }

            public void onFailure(Throwable throwable) {

            }
        });
//        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        while (!threadIsSuc) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                if (!threadIsSuc) {
                    System.out.println("i'm waiting...");
                }
            }

        }
    }
}
