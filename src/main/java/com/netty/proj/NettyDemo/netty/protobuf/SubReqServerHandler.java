package com.netty.proj.NettyDemo.netty.protobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ctg on 2016/11/11.
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
        if ("tg".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscribe req:[ " +req.toString() +" ]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder resp =
                SubscribeRespProto.SubscribeResp.newBuilder();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("your order success, 3 days later,send to the designated address");
        return resp.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
