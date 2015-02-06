package cn.lida.netty.client;

import cn.lida.netty.protocol.CatPortoBuf;
import cn.lida.netty.protocol.MessageHolderProtobuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-23
 * Email:zhanglida@huoqiu.cn
 */
public class SocketClientBizHandler extends SimpleChannelInboundHandler<MessageHolderProtobuf> {
    Logger logger = LoggerFactory.getLogger(SocketClientBizHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageHolderProtobuf messageHolderProtobuf) throws Exception {
        CatPortoBuf.TestResponse joinCatRoomResponse = (CatPortoBuf.TestResponse) messageHolderProtobuf.getParamObj();
        TimeCostStat.getInstance().incrReceiveCount();
    }
}
