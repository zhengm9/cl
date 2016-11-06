package com.zhengm.synchroniztest;

/**
 * Created by zhengming on 16/11/6.
 */
public class SynchronizedClass2 {
    private int i = 0;
    public  void test(){
        synchronized (this){
            while (i<=5){
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":print-"+i);
                i++;
            }
        }
    }

    private final int[] lock2 = new int[0];
    public void test2(){
        {
            int j = 0;
            synchronized (lock2){
                while (j<=5){
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":print-"+j);
                    j++;
                }
            }
        }
    }

    private final int[] lock3 = new int[0];
    public void test3(){
        {
            int j = 0;
            synchronized (lock3){
                while (j<=5){
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":print-"+j);
                    j++;
                }
            }
        }
    }

    private static  int[] lock4 = new int[0];
    public void test4(){
        {
            int j = 0;
            synchronized (lock4){
                while (j<=5){
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":print-"+j);
                    j++;
                }
            }
        }
    }

    public void test5(){
        {
            int j = 0;
            synchronized (SynchronizedClass2.class){
                while (j<=5){
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":print-"+j);
                    j++;
                }
            }
        }
    }

    public synchronized void test6(){
        int i = 0;

        while (i<=5){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":print-"+i);
            i++;
        }
    }

    public static synchronized void test7(){
        int i = 0;

        while (i<=5){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":print-"+i);
            i++;
        }
    }
}
