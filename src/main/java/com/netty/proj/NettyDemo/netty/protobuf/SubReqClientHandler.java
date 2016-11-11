package com.netty.proj.NettyDemo.netty.protobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctg on 2016/11/11.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder req = SubscribeReqProto.SubscribeReq.newBuilder();
        req.setSubReqID(i);
        req.setUserName("tg");
        req.setProductName("Netty Book For Protobuf");
        List<String> address = new ArrayList<>();
        address.add("shenzheng");
        address.add("shanghai");
        address.add("guangzhou");
        address.add("beijing");
        req.addAllAddress(address);
        return req.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response: ["+ msg +" ]" );
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read complete...");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
