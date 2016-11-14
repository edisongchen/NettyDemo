package com.netty.proj.NettyDemo.netty.protocol.nettty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * Created by ctg on 2016/11/12.
 */
@ChannelHandler.Sharable
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) throws Exception {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            //在lengthPos后写入四个字节，值为msg的字节长度
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}
