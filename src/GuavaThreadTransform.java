import com.google.common.base.Function;
import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhengming on 16/11/6.
 */
public class GuavaThreadTransform {
    private static final int THREAD_NUM = 10;
    private volatile boolean threadIsSuc = false;
    private int[] lock = new int[0];
    ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREAD_NUM));

    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {

        ListenableFuture<Integer> future1 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    System.out.println(Thread.currentThread().getName() + "-start");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 10;
            }
        });

        //Futures.transform转换结果
        ListenableFuture<Person> transformedFuture = Futures.transform(future1, new Function<Integer, Person>() {
            public Person apply(Integer list) {
                System.out.println("start to transform sync..");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("finish transform sync!");

                return new Person("hi");
            }
        });

        System.out.println("transform sync finished?" + transformedFuture.get().getName());


        ListenableFuture<Integer> future2 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "-start");
                try {
                    Thread.sleep(1000);
//                    throw new Exception("future2 error");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 20;
            }
        });

        //Futures.allAsList：对多个ListenableFuture的合并，
        // 返回一个当所有Future成功时返回多个Future返回值组成的List对象。
        // 注：当其中一个Future失败或者取消的时候，将会进入失败或者取消。
        ListenableFuture allAsListFuture = Futures.allAsList(transformedFuture, future2);

        //Futures.addCallback:在线程结束时，理解调用FutureCallBack()
        Futures.addCallback(allAsListFuture, new FutureCallback<List>() {
            public void onSuccess(List list) {
                synchronized (lock) {
                    threadIsSuc = true;
                    System.out.println("future success! size:" + list.size());
                    for (Object o : list) {
                        System.out.println("Object class:" + o.getClass());
                        if (o instanceof Person) {
                            System.out.println("Person name:" + ((Person) o).getName());
                        }
                    }
                }
            }

            public void onFailure(Throwable throwable) {
                System.out.println("why onFailure?" + throwable.getMessage());
                System.out.println("why onFailure?" + throwable.getCause());
            }
        });


        ListenableFuture<Integer> future3 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "-start");
                try {
                    Thread.sleep(1000);
                    throw new Exception("future3 error");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 20;
            }
        });

        // successfulAsList：和allAsList相似，唯一差别是对于失败或取消的Future返回值用null代替。不会进入失败或者取消流程。
        List futureList = (List) Futures.successfulAsList(allAsListFuture, future3).get();
        for (Object o : futureList) {
            System.out.println("o:" + o);
        }

    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> future2 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "-start");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 20;
            }
        });


        ListenableFuture<Person> finalAsyncFuture = Futures.transformAsync(future2, new AsyncFunction<Integer, Person>() {
            public ListenableFuture<Person> apply(Integer list) {
                System.out.println("start to transform async..");
                /*try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println("finish transform async!");

                return Futures.immediateFuture(new Person("hi"));
            }
        });

        System.out.println("transform async finished?" + finalAsyncFuture.get().getName());

    }
}
