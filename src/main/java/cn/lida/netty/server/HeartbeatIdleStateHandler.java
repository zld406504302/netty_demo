/**
 * $Id: XGameIdleHandler.java 3017 2014-06-03 02:26:03Z lida.zhang $
 */
package cn.lida.netty.server;

import cn.lida.netty.protocol.CatPortoBuf;
import cn.lida.netty.protocol.CatPortoBuf.PingRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */

@ChannelHandler.Sharable
public class HeartbeatIdleStateHandler extends IdleStateHandler {

    public HeartbeatIdleStateHandler(
            int readerIdleTimeSeconds,
            int writerIdleTimeSeconds,
            int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        if (e.state() == IdleState.READER_IDLE) {
            System.out.println("***************IdleState.READER_IDLE***************");
            ctx.fireChannelUnregistered();
            ctx.channel().closeFuture();
            ctx.channel().close();
        } else if (e.state() == IdleState.WRITER_IDLE) {
            ctx.channel().write(PingRequest.newBuilder().setCommand(CatPortoBuf.Command.CMD_PING));
        }
    }
}
