package com.netty.proj.NettyDemo.netty.messagepack;

import com.netty.proj.NettyDemo.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ctg on 2016/11/10.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User[] users = userInfo();
        for(int i=0;i< users.length;i++){
            ctx.write(users[i]);
        }
        ctx.flush();
    }

    private User[] userInfo() {
        User[] users = new User[sendNumber];
        User user = null;
        for(int i=0;i<sendNumber;i++){
            user = new User();
            user.setName("ABCDEFG---->"+i);
            user.setAge(i);
            users[i] = user;
        }
        return users;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgpack message:" +msg);
        //ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
