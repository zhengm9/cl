//import java.util.concurrent.Executors;

import com.google.common.collect.ObjectArrays;
import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.nio.channels.Pipe;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by zhengming on 16/11/6.
 */
public class GuavaThread {
    private static final int THREAD_NUM = 10;
    private volatile boolean threadIsSuc = false;
    private int[] lock = new int[0];

    @Test
    public void test() throws ExecutionException, InterruptedException {

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREAD_NUM));
        ListenableFuture<Integer> future = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    Thread.sleep(5000);
                    throw new Exception("unable to finish task!", new Throwable());
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
                    lock = new int[2];

                }
            }

            public void onFailure(Throwable throwable) {
                System.out.println("why onFailure?"+throwable.getMessage());
                System.out.println("why onFailure?"+throwable.getCause());
            }
        });

        while (!threadIsSuc) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                if (!threadIsSuc) {
                    System.out.println("I have waited for 1 more second...");
                }else{
                    System.out.println("no longer wait...");

                }
            }

        }
//        System.out.println("future.get():"+future.get());
        while (true){
            if(future.isDone())break;
        }
        System.out.println("all finished.");
    }
}
