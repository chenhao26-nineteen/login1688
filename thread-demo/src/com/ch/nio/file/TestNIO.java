package com.ch.nio.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件NIO操作
 * @author Chaos
 */
public class TestNIO {

    /**
     * 使用nio写文件
     */
    @Test
    public void testWrite() throws Exception{
        //1、获取文件输出流
        FileOutputStream fis = new FileOutputStream("hello.txt");
        //2、通过流获取通道
        FileChannel fileChannel = fis.getChannel();
        //3、准备一个缓冲区,并设置初始大小：ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4、向缓冲区中添加数据
        String msg = "hello,nio，你好呀";
        buffer.put(msg.getBytes());
        //5、翻转缓冲区,由于在读取缓冲区中的数据时，通道是从尾部开始读取的，所以需要重置指针的位置到顶部
        buffer.flip();
        //6、将缓冲区写入通道，通道会自动讲数据写入文件
        fileChannel.write(buffer);
        //7、关闭流
        fis.close();
    }

    /**
     * 使用nio读取文件内容
     */
    @Test
    public void testRead() throws Exception{
        File file = new File("hello.txt");
        //1、创建文件输入流对象
        FileInputStream fis = new FileInputStream(file);
        //2、通过输入流对象获取管道
        FileChannel fileChannel = fis.getChannel();
        //3、准备缓冲区,此处的大小应严格按照文件的大小来设置
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //4、将数据读到缓冲区,并输入读取到的数据
        fileChannel.read(buffer);
        //将buffer转为字节数组
        byte[] bytes = buffer.array();
        System.out.println(new String(bytes));
        //5、关闭流
        fis.close();
    }

    /**
     * 复制文件
     */
    @Test
    public void testCopyFile() throws Exception{
        //1、创建文件输入流和输出流
        FileInputStream fis = new FileInputStream("hello.txt");
        FileOutputStream fos = new FileOutputStream("E:\\nio_copy\\hello.txt");

        //2、创建两个流对象
        FileChannel sourceFC = fis.getChannel();
        FileChannel destFC = fos.getChannel();

        //3、copy文件,一下两种方式任选一种
        //destFC.transferFrom(sourceFC,0,sourceFC.size());
        sourceFC.transferTo(0,sourceFC.size(),destFC);

        //4、关闭流
        fis.close();
        fos.close();
    }
}
