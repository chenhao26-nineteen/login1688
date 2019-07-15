package com.ch.nio.socket;

import java.util.Scanner;
import java.util.concurrent.ThreadFactory;

/**
 * 客户端主方法
 */
public class MainChatClient {


    public static void main(String[] args) throws Exception{

        ChatClient chatClient = new ChatClient();
        //不知道服务端什么时候发送数据过来，所以需要一个新的线程不断接收服务端的请求
        //匿名内部类写法
        new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        chatClient.receiveMsg();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
