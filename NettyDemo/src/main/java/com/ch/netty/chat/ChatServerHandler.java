//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ch.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
	private static List<Channel> channelList = new ArrayList();

	public ChatServerHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel inChannel = ctx.channel();
		channelList.add(inChannel);
		System.out.println("客户端[" + inChannel.remoteAddress().toString().substring(1) + "]上线");
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel inChannel = ctx.channel();
		channelList.remove(inChannel);
		System.out.println("客户端：[" + inChannel.remoteAddress().toString().substring(1) + "]下线");
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
		Channel currentChannel = ctx.channel();
		System.out.println("客户端[" + currentChannel.remoteAddress().toString().substring(1) + "]发送过来数据：" + s);
		Iterator var4 = channelList.iterator();

		while(var4.hasNext()) {
			Channel channel = (Channel)var4.next();
			System.out.println(channel);
			System.out.println(currentChannel);
			if (channel != currentChannel) {
				channel.writeAndFlush("[" + currentChannel.remoteAddress().toString().substring(1) + "]说了：" + s);
			}
		}

	}
}
