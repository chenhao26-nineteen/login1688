package com.ch.thread.communication;

/**
 * 线程间通信案例，两个线程交替输出10此1、2、1、2、1、2
 * @author Chaos
 */
public class ThreadFromNum1 extends Thread{

    @Override
    public void run(){
        for (int i = 0;i<10;i++){
            synchronized (MyLock.o) {
                System.out.println(1);
                //唤醒其他线程
                MyLock.o.notify();

                try {
                    //当前线程等待
                    MyLock.o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
