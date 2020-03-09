//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatServer {
	private int port;

	public static void main(String[] args) {
		(new ChatServer(9999)).run();
	}

	public ChatServer(int port) {
		this.port = port;
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		try {
			((ServerBootstrap)((ServerBootstrap)serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)).option(ChannelOption.SO_BACKLOG, 128)).childOption(ChannelOption.SO_KEEPALIVE, true).childHandler(new ChannelInitializer<SocketChannel>() {
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline pipeline = socketChannel.pipeline();
					pipeline.addLast(new ChannelHandler[]{new StringDecoder()});
					pipeline.addLast(new ChannelHandler[]{new StringEncoder()});
					pipeline.addLast(new ChannelHandler[]{new ChatServerHandler()});
				}
			});
			ChannelFuture future = serverBootstrap.bind(9999).sync();
			System.out.println("Server is Statring......");
			future.channel().closeFuture().sync();
		} catch (Exception var8) {
			var8.printStackTrace();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}
}
