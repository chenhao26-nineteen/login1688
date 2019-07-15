package com.ch.thread.communication;

public class ThreadNumTest {

    public static void main(String[] args) {
        new ThreadFromNum1().start();
        new ThreadFromNum2().start();
    }
}
