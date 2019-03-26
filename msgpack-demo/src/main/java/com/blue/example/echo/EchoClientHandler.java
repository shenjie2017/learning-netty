package com.blue.example.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/26 16:25
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echo
 * @Description:
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
    private int counter = 0;

    private UserInfo[] getUserInfos(int sendNumber) {
        if (sendNumber <= 0) {
            sendNumber = 20;
        }

        UserInfo[] infos = new UserInfo[sendNumber];
        for (int i = 0; i < sendNumber; i++) {
            infos[i] = new UserInfo().buildAge(i).buildName("name-" + i);
        }

        return infos;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (UserInfo info : getUserInfos(100)) {
            logger.info("client send data:" + info.toString());
//            ctx.writeAndFlush(info);
            ctx.write(info);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("counter:" + ++counter + " client receive:" + msg);
        ctx.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
