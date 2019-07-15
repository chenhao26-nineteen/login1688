package com.ch.pc;

/**
 * 生产者消费者问题，测试类
 * @author Chaos
 */
public class ProductorAndConsumerTest {
    public static void main(String[] args) {
        new Farmer().start();
        new Child().start();
    }
}
