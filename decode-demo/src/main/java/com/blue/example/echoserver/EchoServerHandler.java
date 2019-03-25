package com.blue.example.echoserver;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/25 16:25
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echoserver
 * @Description:
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(EchoServerHandler.class.getName());
    private int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String separator = "$_";
        logger.info("counter:" + (++counter) + " server receive:" + (String) msg);
        ByteBuf buf = Unpooled.copiedBuffer(("server reply data:" + (String) msg + separator).getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
