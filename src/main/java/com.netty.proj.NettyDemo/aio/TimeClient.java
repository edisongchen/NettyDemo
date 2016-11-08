package com.netty.proj.NettyDemo.aio;

import sun.applet.Main;

/**
 * Created by ctg on 2016/11/8.
 */
public class TimeClient {

    public static void main(String args[]){
        new Thread(new AsyncTimeClientHandler("127.0.0.1",8080),"AIO-AsyncTimeClient-001").start();
    }
}
