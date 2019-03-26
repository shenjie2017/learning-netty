package com.blue.example.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/26 16:15
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echo
 * @Description:
 */
public class EchoClient {
    public void connect(String ip, short port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("frame decoder", new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2))
                                    .addLast("MessagePack decoder", new MsgDecoder())
                                    .addLast("frame encoder", new LengthFieldPrepender(2))
                                    .addLast("MessagePack encoder", new MsgEncoder())
                                    .addLast( "client handler",new EchoClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(ip, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        String ip = "localhost";
        short port = 8888;
        new EchoClient().connect(ip, port);
    }
}
