package cn.lida.netty.client;

import cn.lida.netty.codec.SocketClientDecoder;
import cn.lida.netty.codec.SocketEncoder;
import cn.lida.netty.server.HeartbeatIdleStateHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketClientSetting {
    private static final String DEFAULT_HOST = "0.0.0.0";

    private String host = DEFAULT_HOST;
    private int port = 9999;
    int maxFrameLength = 1024 * 512;
    int lengthFieldLength = 4;
    int initialBytesToStrip = 4;
    private SocketClientDecoder decoder = new SocketClientDecoder() ;
    private SocketEncoder encoder = new SocketEncoder();
    private HeartbeatIdleStateHandler idleHandler = new HeartbeatIdleStateHandler(30,15,0);

    private SocketClientBizHandler bizHandler = new SocketClientBizHandler();

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public SocketClientDecoder getDecoder() {
        return decoder;
    }

    public SocketEncoder getEncoder() {
        return encoder;
    }

    public HeartbeatIdleStateHandler getIdleHandler() {
        return idleHandler;
    }

    public SocketClientBizHandler getBizHandler() {
        return bizHandler;
    }
    public LengthFieldBasedFrameDecoder lfbFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(maxFrameLength, 0,
                lengthFieldLength, lengthFieldLength, initialBytesToStrip);
    }
}
