package com.netty.proj.NettyDemo.netty.protocol.nettty.message;

/**
 * Netty消息实体
 * 由于心跳消息，握手消息可使用header
 * Created by ctg on 2016/11/12.
 */
public class NettyMessage {
    private Header header;
    private Object body; //消息体

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +'}';
    }
}
