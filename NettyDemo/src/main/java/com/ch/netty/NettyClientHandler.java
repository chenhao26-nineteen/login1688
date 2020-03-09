//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	public NettyClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端ctx：" + ctx);
		ctx.writeAndFlush(Unpooled.copiedBuffer("你好服务端...我是客户端", CharsetUtil.UTF_8));
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		System.out.println("接收服务端发来的数据：" + buf.toString(CharsetUtil.UTF_8));
	}
}
