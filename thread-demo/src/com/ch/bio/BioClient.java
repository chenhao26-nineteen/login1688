package com.ch.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Chaos
 * BIO客户端
 */
public class BioClient {

    public static void main(String[] args) throws Exception{
        //连接服务端
        Socket socket = new Socket("127.0.0.1",9999);
        while (true){
            OutputStream os = socket.getOutputStream();
            System.out.println("请输入：");
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();

            os.write(msg.getBytes());
            //接收服务端回发的数据,阻塞
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[20];
            is.read(bytes);
            System.out.println("服务端回复："+new String(bytes).trim());

            socket.close();
        }
    }

}
