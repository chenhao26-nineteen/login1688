package com.ch.thread;

public class TestSalesWindows {

    public static void main(String[] args) {
        SaleWindow sw = new SaleWindow();
        //模拟两个售票窗口卖票
        Thread t1 = new Thread(sw);
        Thread t2 = new Thread(sw);

        t1.setName("窗口A");
        t2.setName("窗口B");

        t1.start();
        t2.start();
    }
}
