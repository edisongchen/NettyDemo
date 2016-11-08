package com.netty.proj.NettyDemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.omg.CORBA.SystemException;

public class TimeClient {

	public static void main(String[] args) {
		int port= 8080;
		if (args !=null && args.length >0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		new Thread(new TimeClientHandler("127.0.0.1",port),"TimeClient-001").start();
	}
}

