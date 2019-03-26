package com.blue.example;

import junit.framework.TestCase;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/26 14:20
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.EchoServer.echo.EchoServer
 * @Description:
 */

public class MsgPackTestCase extends TestCase {

    private MessagePack msgpack = new MessagePack();

    public void testMsgPackDemo() throws IOException {
        List<String> src = new ArrayList<String>();
        src.add("msgpack");
        src.add("protobuf");
        src.add("thrift");

        byte[] raw = msgpack.write(src);

        System.out.println("raw:" + new String(raw));

        List<String> dst = msgpack.read(raw, Templates.tList(Templates.TString));

        System.out.println("dst:" + dst);
    }
}
