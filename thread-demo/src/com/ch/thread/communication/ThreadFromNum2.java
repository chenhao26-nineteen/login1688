package com.ch.thread.communication;

/**
 * @author Chaos
 * 线程输出 2
 */
public class ThreadFromNum2 extends Thread {
    @Override
    public void run(){
        for (int i = 0;i<10;i++){
            synchronized (MyLock.o) {
                System.out.println(2);
                MyLock.o.notify();

                try {
                    MyLock.o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
