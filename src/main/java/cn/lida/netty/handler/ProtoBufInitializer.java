package cn.lida.netty.handler;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-2-2
 * Email:zhanglida@huoqiu.cn
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
     }

    @Override
    protected void initChannel(Channel ch) throws Exception {

    }
}

