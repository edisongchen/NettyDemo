package com.netty.proj.NettyDemo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 忽略半包处理
 * Created by ctg on 2016/11/8.
 */
public class AsyncTimeServerHandler implements  Runnable {

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel ;

    public AsyncTimeServerHandler(int port){
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port:" +port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);//这里是个demo,为了防止主线程退出
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接受客户端的连接，由于是异步操作，可以传递一个CompletionHandler<AsynchronousSocketChannel,? super A>
     *    类型的handler实力接收accet操作成功的通知消息
     */
    private void doAccept() {
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
