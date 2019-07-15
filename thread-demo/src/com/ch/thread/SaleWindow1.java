package com.ch.thread;

/**
 * 模拟火车票售票问题
 */
public class SaleWindow1 implements Runnable{

    /**
     * 模拟10张票，共享资源
     */
    private int id = 10;

    private synchronized void saleOne(){
        if (id>0){
            System.out.println(Thread.currentThread().getName()+"卖了编号为"+id+"的火车票");
            id--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        for (int i=0;i<10;i++){
//            StringBuffer
           saleOne();
        }
    }
}
