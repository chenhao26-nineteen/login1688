package com.ch.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO客户端程序
 */
public class NIOClient {

    public static void main(String[] args) throws Exception {
        //1、得到SocketChannel通道,使用SocketChannel的静态方法open()
        SocketChannel socketChannel = SocketChannel.open();

        //2、设置非阻塞方式
        socketChannel.configureBlocking(false);

        //3、连接服务器的ip和端口，使用InetSocketAddress对象
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        //4、连接服务器端，判断，如果没连接到服务端，尝试再次一直连接
        if (!connect) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接服务器的过程中我还能做点别的事情...");
            }
        }

        //5、得到一个缓冲区并存储数据
        String msg = "hello server!";
       /* ByteBuffer allocate = ByteBuffer.allocate(msg.getBytes().length);
        allocate.put(msg.getBytes());
        //翻转字节缓冲区
        allocate.flip();*/

        //可以使用这一行代码替换以上的三行代码
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        //6、使用管道发送数据给服务器
        socketChannel.write(buffer);

        //7、此时不能关闭客户端连接，会报错。程序不能停止
        System.in.read();
    }
}
