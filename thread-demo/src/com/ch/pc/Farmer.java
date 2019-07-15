package com.ch.pc;

/**
 * @author Chaos
 * 生产者消费者问题
 * Farmer：生产者
 */
public class Farmer extends Thread{

    @Override
    public void run(){
        //使用死循环保证一直在生产
        while (true) {
            synchronized (Basket.list){

                //农夫生产苹果
                Basket.list.add("apple");
                System.out.println("农夫放了1个水果，目前框里有" + Basket.list.size() + "个苹果");
                //只要有苹果就唤醒小孩吃
                Basket.list.notify();

                //如果生产的苹果数量等于10,篮子装满，农夫休息
                if (Basket.list.size() == 10) {
                    try {
                        Basket.list.wait();
                        System.out.println("农夫又放了一个苹果，苹果满了，农夫暂时休息一会儿...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                //睡眠200ms，模拟生产的速度
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
