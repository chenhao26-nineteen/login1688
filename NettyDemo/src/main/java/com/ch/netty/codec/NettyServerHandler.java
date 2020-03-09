//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pojo.BookMessage.Book;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	public NettyServerHandler() {
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Book book = (Book)msg;
		System.out.println("客户端发送给服务端的数据：" + book.getName());
	}
}
