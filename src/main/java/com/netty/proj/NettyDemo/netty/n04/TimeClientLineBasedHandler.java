package com.netty.proj.NettyDemo.netty.n04;

import com.netty.proj.NettyDemo.util.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ctg on 2016/11/9.
 */
public class TimeClientLineBasedHandler extends ChannelInboundHandlerAdapter {
    private int counter;
    private byte[] req;


    public TimeClientLineBasedHandler() {
        req = ("QUERY TIME ORDER"+ Constants.LINE_SEPARATOR).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message =null;
        for(int i=0;i<100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String )msg;
        System.out.println("Now is :"+body +" ; the counter is:"+ ++counter);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read complete..");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("execption :"+cause.getMessage());
        ctx.close();
    }
}
