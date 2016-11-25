package com.zhengm.synchroniztest;


import com.google.common.util.concurrent.Monitor;
import org.junit.Test;

/**
 * Created by zhengming on 16/11/13.
 */
public class NotifyTest {
    public class NotifySafeBox<V> {
        private V value;
        public synchronized V get() throws InterruptedException {
            while (value == null) {
                wait();
            }
            V result = value;
            value = null;
            notifyAll();
            return result;
        }
        public synchronized void set(V newValue) throws InterruptedException {
            System.out.println("try to set safebox:"+newValue);

//            while (value != null) {
            if(value!=null){
                wait();
                System.out.println("wait end:"+newValue);
            }
            value = newValue;
            System.out.println("safebox set:"+newValue);

            notifyAll();
        }
    }

    public class MonitorSafeBox<V> {
        private V value;
        private Monitor monitor = new Monitor();
        private Monitor.Guard absent = new Monitor.Guard(monitor) {
            public boolean isSatisfied() {
                return value == null;
            }
        };

        private Monitor.Guard present = new Monitor.Guard(monitor){
            public boolean isSatisfied() {
                return value != null;
            }
        };
        public synchronized V get() throws InterruptedException {
            monitor.enterIf(present);

            V result = value;
            value = null;
            monitor.leave();
            return result;
        }
        public synchronized void set(V newValue) throws InterruptedException {
            System.out.println("try to set safebox:"+newValue);

//            while (value != null) {
            if(value!=null){
                wait();
                System.out.println("wait end:"+newValue);
            }
//            }
            value = newValue;
            System.out.println("safebox set:"+newValue);

            notifyAll();
        }
    }

    @Test
    public  void test1()
    {
        NotifyTest n = new NotifyTest();
        final NotifySafeBox<String> safebox =  n.new NotifySafeBox();
        try {
            safebox.set("hi");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        safebox.set("hi2");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

            try {
                safebox.set("hi3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.sleep(3000);
            System.out.println(safebox.get());;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}


