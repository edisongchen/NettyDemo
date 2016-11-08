package com.netty.proj.NettyDemo.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by ctg on 2016/11/8.
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        System.out.println("completed..");
        //这里再次调用accept方法，如果有新的客户端连接介入，系统将回掉CompletionHandler这个实例
        //的completed方法，表示新的客户端连接成功，由于AsynchronousServerSocketChannel 能接受成千上万的
        //客户端来凝结，所有需要继续调用accept方法，接收其它客户端连接，最终形成一个循环。每次接收一个客户端读连接成功后，
        //再异步接收新的客户端连接
        attachment.asynchronousServerSocketChannel.accept(attachment,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //read 异步读操作，read的方法参数 分析
        //ByteBuffer dst: 接收缓冲区，用于从异步channel中读取数据包
        //A attachment 异步Channel携带的附件，通知回调的时候作为入参使用
        //CompletionHandler<Integer,? super A>: 接收通知回掉的业务handler
        result.read(buffer,buffer,new ReadCompletionHandler(result));
        //异步读取消息，并使用异步写入通道
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
        System.out.println("failed");
    }
}
