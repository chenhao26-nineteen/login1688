//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
	public ChatClientHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
		System.out.println(s);
	}
}
