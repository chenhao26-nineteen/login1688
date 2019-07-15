package com.ch.nio.socket;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 聊天程序客户端
 */
public class ChatClient {

    private static final  String IP = "127.0.0.1";
    private static final int PORT = 9999;

    private static SocketChannel socketChannel;

    private String username = "";


    public ChatClient(){
        try {
            //1、获取通道
            socketChannel  = SocketChannel.open();
            //2、设置非阻塞方式
            socketChannel.configureBlocking(false);
            //3、绑定服务端的ip和端口
            InetSocketAddress address = new InetSocketAddress(IP, PORT);
            //4、连接服务端
            if (!socketChannel.connect(address)){
                while (!socketChannel.finishConnect()) {
                    System.out.println("在连接服务器的过程中，我还可以干别的事情");

                }
            }

            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("client is ("+username+") is ready...");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 发送数据给服务器端
     */
    public void sendMsg(String msg)  throws Exception{

        //如果对方发送的是bye，那么聊天结束，结束连接
        if (msg!=null && msg.equalsIgnoreCase("bye")) {
            //关闭通道
            socketChannel.close();
            return;
        }
        msg = username +"说："+msg;
        //得到缓冲区
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        //发送数据
        socketChannel.write(buffer);
    }

    /**
     * 从服务端接收广播过来的数据
     */
    public void receiveMsg() throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int size = socketChannel.read(buffer);
        if (size>0) {
            String msg = new String(buffer.array());
            System.out.println(msg.trim());
        }
    }
}
