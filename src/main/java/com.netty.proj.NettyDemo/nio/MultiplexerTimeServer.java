package com.netty.proj.NettyDemo.nio;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{
	
	private Selector selector;
	private ServerSocketChannel servChannel;
	private volatile boolean stop;
	
	public MultiplexerTimeServer() {
		super();
	}

	public  MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();//创建Reactor线程，创建多路复用器并启动线程
			servChannel= ServerSocketChannel.open();//打开ServerSocketChannel,用于监听客户端连接，它是所有客户端连接的父管道
			servChannel.socket().bind(new InetSocketAddress(port),1024);
			servChannel.configureBlocking(false);
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port :"+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(!stop){
			try {
				selector.select(1000);// 设置复用器的最大key数量
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while(it.hasNext()){
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					}catch (Exception e){
						System.out.println("handleInput getException:" +e.getMessage());
						if (key !=null) {
							key.cancel();
							if (key.channel() !=null) {
								key.channel().close();
							}
						}
					}
				}
				
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		//多路复用器关闭后，所有注册到上面的Channel和pipe等资源都会被自动注册并关闭，所以不需要重复释放资源
		if (selector !=null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			//处理介入的请求消息
			if (key.isAcceptable()) {
				//Accept the new Connection
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				//目的是注册到多路复用器上
				sc.register(selector, SelectionKey.OP_READ);
			}


			if (key.isReadable()) {
				//Read the data
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes >0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("The Time server receive order:"+body);
					String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?
							new Date(System.currentTimeMillis()).toString():"BAD ORDER";
					doWrite(sc,currentTime);
				 } else if(readBytes <0){
					//对端链路关闭
					key.cancel();
					sc.close();
				} else {
					System.out.println("read 0 byte length....");
				}

			}
		}
	}

	private void doWrite(SocketChannel channel, String response) throws IOException {
		if (response !=null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}

}
