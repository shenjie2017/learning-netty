package com.blue.client;

import com.blue.handle.TransClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shenjie
 * @version v1.0
 * @Description
 * @Date: Create in 11:46 2018/1/3
 * @Modifide By:
 **/

//      ┏┛ ┻━━━━━┛ ┻┓
//      ┃　　　　　　 ┃
//      ┃　　　━　　　┃
//      ┃　┳┛　  ┗┳　┃
//      ┃　　　　　　 ┃
//      ┃　　　┻　　　┃
//      ┃　　　　　　 ┃
//      ┗━┓　　　┏━━━┛
//        ┃　　　┃   神兽保佑
//        ┃　　　┃   代码无BUG！
//        ┃　　　┗━━━━━━━━━┓
//        ┃　　　　　　　    ┣┓
//        ┃　　　　         ┏┛
//        ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//          ┃ ┫ ┫   ┃ ┫ ┫
//          ┗━┻━┛   ┗━┻━┛

public class TransClient {
    private String remoteIP;
    private int remotePort;
    private volatile boolean isConnected = false;
    private TransClientHandler clientHandler = null;
    private int connectCount = 0;
    private final int CONNECTMAXCOUNT = 20;
    private volatile boolean isClose = false;

    public TransClient(String remoteIP, int remotePort) {
        this.remoteIP = remoteIP;
        this.remotePort = remotePort;

        //加入断线重连机制
        refreshConnect();
    }

    public void send(Object msg){
        if(!isConnected){
            System.out.println(remoteIP+":"+remotePort+"已断开连接");
            return;
        }
        clientHandler.sendMsg("forward:"+msg);

    }

    public void connect(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    clientHandler = new TransClientHandler();
                    ch.pipeline().addLast(new StringEncoder()).addLast(new StringDecoder()).addLast(clientHandler);
                }
            });

            ChannelFuture f = b.connect(remoteIP,remotePort).sync();
            isConnected = true;
            connectCount++;
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
        isConnected = false;;
    }

    public void refreshConnect(){
        //没过10秒检测是否断线
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable(){

            public void run() {
                if(isClose){
                    return;
                }

                if(isConnected){
                    return;
                }
                if(connectCount>CONNECTMAXCOUNT){
                    return;
                }

                connect();
            }
        },0 , 10, TimeUnit.SECONDS);
    }

    public void close(){
        if(isClose){
            return;
        }
        isClose = true;
        clientHandler.close();
    }
}
