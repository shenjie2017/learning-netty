package com.blue.example.echo;


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
        logger.info("counter:" + ++counter + " server receive:" + msg);

        UserInfo info = (UserInfo) msg;
        info.setAge(info.getAge() + 1);

        ctx.writeAndFlush(msg);
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
