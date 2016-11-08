package com.netty.proj.NettyDemo.aio;

/**
 * jdk1.7 AIO
 * Created by ctg on 2016/11/8.
 */
public class TimeServer {
    public static void main(String args[]){
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(8080);
        new Thread(timeServer,"AIO-AsyncTimeServerHandler-001").start();
    }
}
