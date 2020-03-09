//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.work;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	public NettyClientHandler() {
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是客户端", CharsetUtil.UTF_8));
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("客户端：" + ctx);
		ByteBuf buf = (ByteBuf)msg;
		System.out.println("服务端发送过来的数据：" + buf.toString(CharsetUtil.UTF_8));
	}
}
