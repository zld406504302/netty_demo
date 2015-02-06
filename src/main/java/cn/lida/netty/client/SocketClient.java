/**
 * $Id: XGameServer.java 3285 2014-06-23 03:19:19Z lida.zhang $
 */
package cn.lida.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketClient {

    private Bootstrap bootstrap;

    private SocketClientSetting serverSetting;

    private Channel channel;

    public SocketClient init() throws InterruptedException {
        initSocketServer();
        return this;
    }

    private void initSocketServer() {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.option(ChannelOption.SO_RCVBUF, 256 * 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 256 * 1024);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            public void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("LFBFDecoder", serverSetting.lfbFrameDecoder());
                pipeline.addLast("decoder", serverSetting.getDecoder());
                pipeline.addLast("encoder", serverSetting.getEncoder());
                pipeline.addLast("idleHandler", serverSetting.getIdleHandler());
                pipeline.addLast("handler", serverSetting.getBizHandler());
            }
        });

        try {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(serverSetting.getHost(), serverSetting.getPort())).sync();
            channel = future.channel();//bootstrap.connect(serverSetting.getHost(),serverSetting.getPort()).sync().channel();
            group.register(channel).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("**********************connect isSuccess*************");
                    }
                    if(future.isDone()){
                        System.out.println("**********************task done*********************");
                    }
                    else {
                        System.out.println("register channel on EventLoop fail");
                        future.cause().printStackTrace();
                    }
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SocketClient setServerSetting(SocketClientSetting serverSetting) {
        this.serverSetting = serverSetting;
        return this;
    }


    public Channel getChannel() {
        return channel;
    }

}
