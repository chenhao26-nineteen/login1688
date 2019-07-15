package com.ch.nio.socket;

import sun.misc.Cleaner;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Chaos
 * 聊天程序，服务端代码
 */
public class ChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int PORT = 9999;

    /**
     * main方法，程序的入口
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        //启动服务端
        new ChatServer().start();
    }

    /**
     * 初始化客户端连接
     */
    public ChatServer(){
        try {
            //1、获取服务端通道，老大
            serverSocketChannel = ServerSocketChannel.open();

            //2、获取选择器对象，监听服务端事件，间谍
            selector = Selector.open();

            //3、绑定服务端的端口
            serverSocketChannel.bind(new InetSocketAddress(PORT));

            //4、设置通道非阻塞的方式
            serverSocketChannel.configureBlocking(false);

            //5、将ServerSocketChannel对象注册给Selector对象
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            printInfo("Chat Server is ready");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  搞事情
     */
    public void start() throws Exception{
        while (true){
            //监听客户端的连接,每两秒检测一次
            if (selector.select(2000) == 0){
                System.out.println("没有客户端连接我，我可以干别的事...");
                continue;
            }

            //一旦检测到客户端连接,通过选择器获取SelectionKey集合，遍历并判断时间处理
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {

                SelectionKey selectionKey = keyIterator.next();

                //判断是否为连接事件
                if (selectionKey.isAcceptable()) {
                    //获取客户端通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //注册选择器
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    String clientIp = socketChannel.getRemoteAddress().toString().substring(1)+"上线了";
                    System.out.println(clientIp);
                    //printInfo(clientIp);

                }

                //判断是否为客户单读取数据事件
                if (selectionKey.isReadable()) {
                    //读取数据并发送广播
                    readMsg(selectionKey);
                }
                /**
                 * 一定一定一定一定一定一定，切记要把当前key删掉，防止重复处理，造成空指针异常
                 *
                 *
                 *
                 */
                keyIterator.remove();
            }
        }
    }

    /**
     * 读取数据
     * @param selectionKey 选择器对象，监听所有的客户端连接
     * @throws Exception
     */
    private  void readMsg(SelectionKey selectionKey) throws Exception{
        //获取通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //准备缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int count = socketChannel.read(byteBuffer);
        //判断是否读取到数据
        if (count>0){
            //获取读取到的数据
            String msg = new String(byteBuffer.array());
            //打印数据到控制台
            printInfo(msg);
            //发广播
            borderCast(socketChannel,msg);
        }
    }

    /**
     * 发送广播方法
     * @param except 需要排除的通道
     * @param msg 需要对外广播的数据
     */
    public void borderCast(SocketChannel except,String msg) throws Exception{
        System.out.println("服务器发送了广播");
        /**
         * 获取所有的通道
         */
        Set<SelectionKey> selectionKeys = selector.keys();
        for (SelectionKey selectionKey : selectionKeys) {
            //获取每一个通道对象，并判断targetChannel是否属于SocketChannel对象并且不是当前发消息的对象（自己发的消息再发送给自己没有意义）
            Channel targetChannel = selectionKey.channel();
            if (targetChannel instanceof SocketChannel && targetChannel!=except) {
                //强转进行消息的广播
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                //准备缓冲区（通道的消息都需要在缓冲区中传递的）
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将缓冲区写入通道
                socketChannel.write(buffer);
            }
        }
    }

    /**
     * 打印上线信息
     * @param clientIp
     */
    private void printInfo(String clientIp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH;mm:ss");
        System.out.println("["+sdf.format(new Date())+"]"+clientIp);
    }
}
