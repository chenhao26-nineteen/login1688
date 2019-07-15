package com.ch.pc;

/**
 * 消费者，小孩吃苹果
 * @author Chaos
 */
public class Child extends Thread {

    @Override
    public void run() {
        //使用死循环保证一直在消费
        while (true) {
            synchronized (Basket.list) {

                //小孩吃苹果(将苹果从容器中移除)
                Basket.list.remove("apple");
                System.out.println("小孩子吃掉了1个苹果，框中还剩" + Basket.list.size() + "个苹果");
                //只要吃掉一个就调唤醒农夫，让农夫继续生产
                Basket.list.notify();

                //如果消费完了，小孩子休息
                if (Basket.list.size() == 0) {
                    try {
                        Basket.list.wait();
                        System.out.println("小孩又吃了一个苹果，框中没有苹果了，小孩子休息啦。。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                //模拟消费的速度
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
