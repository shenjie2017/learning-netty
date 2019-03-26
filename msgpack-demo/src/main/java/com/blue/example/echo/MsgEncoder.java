package com.blue.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/26 15:56
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echo
 * @Description:
 */
public class MsgEncoder extends MessageToByteEncoder {
    private MessagePack msgpack = new MessagePack();

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeBytes(msgpack.write(msg));
    }

}
