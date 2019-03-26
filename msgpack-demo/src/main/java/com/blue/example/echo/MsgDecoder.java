package com.blue.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/26 16:02
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echo
 * @Description:
 */
public class MsgDecoder extends MessageToMessageDecoder<ByteBuf> {
    private MessagePack msgpack = new MessagePack();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] src = new byte[in.readableBytes()];
        in.getBytes(in.readerIndex(), src, 0, src.length);
        out.add(msgpack.read(src));
    }

}
