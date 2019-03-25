package com.blue.example.echoserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: Jason
 * @E-mail: 1075850619@qq.com
 * @Date: create in 2019/3/25 16:47
 * @Modified by:
 * @Project: learning-netty
 * @Package: com.blue.example.echoserver
 * @Description:
 */
public class EchoClient {

    private String separator = "$_";

    public void connect(String ip, short port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer(separator.getBytes());
                            socketChannel.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(4096, delimiter))
                                    .addLast(new StringDecoder())
                                    .addLast(new EchoClientHandler());
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
