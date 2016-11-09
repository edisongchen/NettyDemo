package com.netty.proj.NettyDemo.netty.n05;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ctg on 2016/11/9.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private int count;
    static final String ECHO_REQ = "Hi, my lady. welcome to Netty.$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is "+ ++count +" times receive from server:["+msg +"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("read complete...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("execption:"+cause.getMessage());
        ctx.close();
    }
}
