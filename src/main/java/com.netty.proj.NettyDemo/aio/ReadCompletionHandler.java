package com.netty.proj.NettyDemo.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * Created by ctg on 2016/11/8.
 */
public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler() {
    }

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null ) {
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        System.out.println("async read complete");
        attachment.flip();
        byte [] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body,"UTF-8");
            System.out.println("The time server receive order:"+req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req)?
                    new Date(System.currentTimeMillis()).toString():"BAD ORDER";
            doWrite(currentTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void doWrite(String currentTime) {
        if (currentTime !=null && currentTime.trim().length() >0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            //与异步read 类似
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    //如果没有发送完成，继续发送
                    if (buffer.hasRemaining()) {
                        channel.write(buffer,buffer,this);
                    }
                    System.out.println("///异步读取完成后，异步返回响应发送完毕");
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        //当异常发生的时候，需要对类型进行处理，这里简单的只是关闭了链路，释放资源
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
