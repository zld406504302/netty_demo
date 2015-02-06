package cn.lida.netty.server;

import cn.lida.netty.codec.SocketDecoder;
import cn.lida.netty.codec.SocketEncoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketServerSetting {
    private static final String DEFAULT_HOST = "0.0.0.0";

    private String host = DEFAULT_HOST;
    private int port = 9999;
    int maxFrameLength = 1024 * 64;
    int lengthFieldLength = 4;
    int initialBytesToStrip = 4;
    private SocketDecoder decoder = new SocketDecoder() ;
    private SocketEncoder encoder = new SocketEncoder();
    private HeartbeatIdleStateHandler idleHandler = new HeartbeatIdleStateHandler(30,15,0);

    private SocketServerBizHandler bizHandler = new SocketServerBizHandler();

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public SocketDecoder getDecoder() {
        return decoder;
    }

    public SocketEncoder getEncoder() {
        return encoder;
    }

    public LengthFieldBasedFrameDecoder lfbFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(maxFrameLength, 0,
                lengthFieldLength, lengthFieldLength, initialBytesToStrip);
    }

    public HeartbeatIdleStateHandler getIdleHandler() {
        return idleHandler;
    }

    public SocketServerBizHandler getBizHandler() {
        return bizHandler;
    }

}
