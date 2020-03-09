//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.codec;

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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import pojo.BookMessage.Book;

public class NettyServer {
	public NettyServer() {
	}

	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		((ServerBootstrap)((ServerBootstrap)bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)).option(ChannelOption.SO_BACKLOG, 128)).childOption(ChannelOption.SO_KEEPALIVE, true).childHandler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel serverSocketChannel) throws Exception {
				ChannelPipeline pipeline = serverSocketChannel.pipeline();
				pipeline.addLast("decoder", new ProtobufDecoder(Book.getDefaultInstance()));
				pipeline.addLast(new ChannelHandler[]{new NettyServerHandler()});
			}
		});
		System.out.println("Server is ready......");
		ChannelFuture future = bootstrap.bind(9999).sync();
		System.out.println("Server is Starting");
		future.channel().closeFuture().sync();
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
}
