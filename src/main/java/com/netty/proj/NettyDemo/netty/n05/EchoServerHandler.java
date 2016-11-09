package com.netty.proj.NettyDemo.netty.n05;

import com.netty.proj.NettyDemo.util.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ctg on 2016/11/9.
 * */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
  int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("This is "+ ++counter +" times received client:["+body+"]");
//        body += Constants.DELIMITER_$_;
//        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
//        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("execption:" + cause.getMessage());
        ctx.close();
    }
}
