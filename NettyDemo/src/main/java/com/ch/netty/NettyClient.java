//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	public NettyClient() {
	}

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		((Bootstrap)((Bootstrap)bootstrap.group(group)).channel(NioSocketChannel.class)).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(new ChannelHandler[]{new NettyClientHandler()});
			}
		});
		System.out.println("Client is ready......");
		ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();
		System.out.println("Client is connected......");
		future.channel().closeFuture().sync();
	}
}
