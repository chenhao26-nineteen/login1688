package com.ch.bio;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用传统的Socket编程  服务端
 */
public class BioServer {

    public static void main(String[] args) throws Exception{

        //服务端监听的端口
        ServerSocket serverSocket = new ServerSocket(9999);

        //死循环，服务端一直接收客户端的请求和连接
        while (true){
            //监听客户端是否连接，阻塞状态
            Socket socket = serverSocket.accept();
            //获取服务端的输入流
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[20];
            //读取客户端发送过来的数据
            is.read(bytes);
            String clientIp = socket.getInetAddress().getHostAddress();
            System.out.println("IP："+clientIp+"说了"+new String(bytes).trim());

            //获取客户端输出流对象，向客户端回传数据
            OutputStream os = socket.getOutputStream();
            //以字节的形式传输
            os.write("你好呀".getBytes());

            //关闭客户端连接
            socket.close();
        }
    }
}
