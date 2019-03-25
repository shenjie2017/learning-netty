package com.blue.example.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/25 16:55
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echoserver
 * @Description:
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
    private int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String separator = "$_";
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            String msg = ("msg " + i + ":" + random.nextInt() + " end!!! " + separator);
            ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
            logger.info(msg);
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("counter:" + (++counter) + " client receive:" + (String) msg);
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
