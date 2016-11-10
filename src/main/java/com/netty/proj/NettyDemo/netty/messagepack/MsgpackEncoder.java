package com.netty.proj.NettyDemo.netty.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Messagepack 编码器
 * Created by ctg on 2016/11/10.
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack msgpack = new MessagePack();
        //Serialize
        byte [] raw = msgpack.write(o);
        byteBuf.writeBytes(raw);
        System.out.println("encoder success with size:"+byteBuf.readableBytes());
    }
}
