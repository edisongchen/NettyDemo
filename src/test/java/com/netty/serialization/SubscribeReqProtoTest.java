package com.netty.serialization;

import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.proj.NettyDemo.netty.protobuf.SubscribeReqProto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctg on 2016/11/10.
 */
public class SubscribeReqProtoTest {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("tg");
        builder.setProductName("Netty protobuf");
        List<String> address = new ArrayList<>();
        address.add("Chengdu");
        address.add("shanghai");
        address.add("shengzhen");
        address.add("beijing");
        builder.addAllAddress(address);
        return builder.build();
    }


    @Test
    public void test() throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode:" + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode: " + req.toString());
        System.out.println("Assert equal: -->"+ req2.equals(req));
    }
}
















