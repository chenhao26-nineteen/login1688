package com.ch.nio.socket;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务端程序
 */
public class NIOServer {

    public static void main(String[] args) throws Exception{
        //1、获取服务端网络通道,使用ServerSocketChannel的静态方法open() 老大
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2、获取选择器对象，间谍
        Selector selector = Selector.open();

        //3、绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //4、设置非阻塞的方式
        serverSocketChannel.configureBlocking(false);

        //5、把ServerSocketChannel对象注册给Selector对象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6、搞事情
        while (true) {
            //6.1、监控客户端
            //如果没有客户端连接 2秒之内没有客户端连接就显示空闲状态
            if (selector.select(2000)==0){ //非阻塞的优势
                System.out.println("客户端没连我，我干点别的事情......");
                continue;
            }

            //6.2、得到SelectKey 判断通道里的事件（每个客户端都包含一个SelectionKey）
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
           while (iterator.hasNext()) {
               SelectionKey selectionKey = iterator.next();
               //判断具体监听的事件
                //客户端连接事件
                if (selectionKey.isAcceptable()) {
                    System.out.println("监听到客户端连接事件...");
                    //获取SocketChannel对象
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置非阻塞的方式
                    socketChannel.configureBlocking(false);
                    //注册新连接的对象,监听连接后的客户端读操作
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                //读取客户端数据事件
                if (selectionKey.isReadable()){
                    System.out.println("监听到客户端发送数据。。。");
                    //获取SocketChannel对象
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //准备缓冲区
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    //向缓冲区中读数据
                    channel.read(buffer);
                    System.out.println("客户端发来数据："+new String(buffer.array()));
                }




                //6.3、手动从集合中移除当前key，防止重复处理
                iterator.remove();

            }
        }
    }
}
