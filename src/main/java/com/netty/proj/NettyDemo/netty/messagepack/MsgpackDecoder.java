package com.netty.proj.NettyDemo.netty.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Messagepack 解码器
 * Created by ctg on 2016/11/10.
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf>{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final byte[] array;
        final int length = byteBuf.readableBytes();
        array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
        MessagePack msgpack = new MessagePack();
        Object obj = msgpack.read(array);
        list.add(obj);
        System.out.println("Messagpack decode size:"+ length +" obj:"+obj.toString());
    }
}
