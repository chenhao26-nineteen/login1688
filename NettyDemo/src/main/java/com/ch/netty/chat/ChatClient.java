//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.util.Scanner;

public class ChatClient {
	private String host;
	private int port;

	public static void main(String[] args) {
		(new ChatClient("127.0.0.1", 9999)).run();
	}

	public ChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();

		try {
			((Bootstrap)((Bootstrap)bootstrap.group(group)).channel(NioSocketChannel.class)).handler(new ChannelInitializer<SocketChannel>() {
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline pipeline = socketChannel.pipeline();
					pipeline.addLast(new ChannelHandler[]{new StringDecoder()});
					pipeline.addLast(new ChannelHandler[]{new StringEncoder()});
					pipeline.addLast(new ChannelHandler[]{new ChatClientHandler()});
				}
			});
			ChannelFuture future = bootstrap.connect(this.host, this.port).sync();
			Channel channel = future.channel();
			System.out.println(channel.localAddress().toString().substring(1) + "----------");
			Scanner scanner = new Scanner(System.in);

			while(scanner.hasNextLine()) {
				String msg = scanner.nextLine();
				channel.writeAndFlush(msg + "\r\n");
			}
		} catch (Exception var10) {
			var10.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}
}
