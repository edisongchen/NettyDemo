package com.netty.serialization;

import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MessagePack是一个高效的二进制序列化框架，像json一样支持不同语言间的数据交换
 * 但性能更快，序列化后的码流更小
 * Created by ctg on 2016/11/10.
 */
public class MessagePackTest {

    @Test
    public void testDecodeEncode() throws IOException {
        List<String> src = new ArrayList<>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        MessagePack msgpack = new MessagePack();
        byte[] raw = msgpack.write(src);
        List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));
        System.out.println(dst1.get(0));
        System.out.println(dst1.get(1));
        System.out.println(dst1.get(2));
    }
}