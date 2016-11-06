package com.zhengm.synchroniztest;

/**
 * Created by zhengming on 16/11/6.
 */
public class SynchronizedClass1 {
    public synchronized void test1(){
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

    public synchronized void test2(){
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

    public static synchronized void test3(){
        int j = 0;
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
