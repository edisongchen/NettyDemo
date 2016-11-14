package com.netty.proj.NettyDemo.netty.protocol.nettty.server;

import com.netty.proj.NettyDemo.netty.protocol.nettty.MessageType;
import com.netty.proj.NettyDemo.netty.protocol.nettty.message.Header;
import com.netty.proj.NettyDemo.netty.protocol.nettty.message.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ctg on 2016/11/12.
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whitekList = { "127.0.0.1", "192.168.10.135" };


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手请求消息，处理，其它消息透传
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_REQ
                .value()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            // 重复登陆，拒绝
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel()
                        .remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for (String WIP : whitekList) {
                    if (WIP.equals(ip)) {
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK ? buildResponse((byte) 0)
                        : buildResponse((byte) -1);
                if (isOK)
                    nodeCheck.put(nodeIndex, true);
            }
            System.out.println("The login response is : " + loginResp
                    + " body [" + loginResp.getBody() + "]");
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
