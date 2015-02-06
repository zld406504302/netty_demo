/**
 * $Id: XGameServer.java 3285 2014-06-23 03:19:19Z lida.zhang $
 */
package cn.lida.netty.server;

import cn.lida.netty.codec.SocketDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketServer {

    private ServerBootstrap socketServer;

    private SocketServerSetting serverSetting;

    public void start() throws InterruptedException {
        initSocketServer();
        ChannelFuture f = socketServer.bind().sync();
        f.channel().closeFuture().sync();
    }


    private void initSocketServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        socketServer = new ServerBootstrap();
        socketServer.group(bossGroup, workerGroup);
        socketServer.option(ChannelOption.TCP_NODELAY, true);
        socketServer.option(ChannelOption.SO_KEEPALIVE, true);
        socketServer.option(ChannelOption.SO_REUSEADDR, true);
        socketServer.channel(NioServerSocketChannel.class);
        socketServer.option(ChannelOption.SO_RCVBUF,256*1024);
        socketServer.option(ChannelOption.SO_SNDBUF,256*1024);
        socketServer.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        socketServer.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("LFBFDecoder", serverSetting.lfbFrameDecoder());
                pipeline.addLast("decoder", new SocketDecoder());
                pipeline.addLast("encoder", serverSetting.getEncoder());
                pipeline.addLast("idleHandler", serverSetting.getIdleHandler());
                pipeline.addLast(/*new NioEventLoopGroup() ,*/"handler", serverSetting.getBizHandler());
            }
        });
        socketServer.localAddress(new InetSocketAddress(serverSetting.getHost(), serverSetting.getPort()));
    }

    public void setServerSetting(SocketServerSetting serverSetting){
        this.serverSetting = serverSetting;
    }

}
