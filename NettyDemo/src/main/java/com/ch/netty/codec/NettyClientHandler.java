//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pojo.BookMessage.Book;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	public NettyClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Book book = Book.newBuilder().setId(1).setName("Netty从入门到放弃").build();
		ctx.writeAndFlush(book);
	}
}
