package cn.lida.netty.server;
import cn.lida.netty.protocol.MessageHolderProtobuf;
import com.google.protobuf.GeneratedMessageLite;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketUser {
	private static Logger logger = LoggerFactory.getLogger(SocketUser.class);
	
	private static AtomicLong autoID = new AtomicLong(0);
	/**
	 * 自动生成的id
	 */
	private long id;

	private long userId ;
	//设备ID
	private String deviceId;
	
	private final Channel channel;

	/** 该玩家socket通道是否正常 */
	private volatile boolean connectionOk = true;


	public SocketUser(Channel channel){
        this(channel,0);
	}

    public SocketUser(Channel channel,long userId){
        this.id = getNewID();
        this.channel = channel;
        this.userId = userId;
    }
	
	private static long getNewID(){
	    return autoID.incrementAndGet();
	}
	
	public long getId() {
		return id;
	}


	public Channel getChannel() {
		return channel;
	}

	public void send(MessageHolderProtobuf messageHolderProtobuf) {

        if (!isConnectionOk()) {
            return;
        }

        if (null == messageHolderProtobuf) {
            return;
        }

        GeneratedMessageLite generatedMessageLite = messageHolderProtobuf.getParamObj();
        if (generatedMessageLite != null) {
            channel.writeAndFlush(messageHolderProtobuf);
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        SocketUser.logger = logger;
    }

    public long getUserId(){
        return userId;
    }
    @Override
	public String toString() {
		return this.toString();
	}

	public boolean isConnectionOk() {
		return connectionOk;
	}

}
