/**
 * $Id: XGameBizHandler.java 49 2013-10-17 05:17:47Z lida.zhang $
 */
package cn.lida.netty.server;

import cn.lida.netty.protocol.CatCmd;
import cn.lida.netty.protocol.CatPortoBuf;
import cn.lida.netty.protocol.CatProtocolFacade;
import cn.lida.netty.protocol.MessageHolderProtobuf;
import cn.lida.netty.server.processor.ISocketProcessor;
import cn.lida.netty.util.BeanFactory;
import com.google.protobuf.GeneratedMessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
@ChannelHandler.Sharable
public class SocketServerBizHandler extends SimpleChannelInboundHandler<MessageHolderProtobuf> {
    private static Logger logger = LoggerFactory.getLogger(SocketServerBizHandler.class);
    private SocketUserManager socketUserManager = BeanFactory.getInstance(SocketUserManager.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageHolderProtobuf data) throws Exception {

        Channel channel = ctx.channel();
        int command = data.getCommand();
        // 默认不处理Ping请求
        if (command == CatCmd.CMD_PING) {
            return;
        }

        ISocketProcessor processor = CatProtocolFacade.getProcessor(command);
        if (null == processor) {
            logger.warn("[ProtocolError][unknown command:" + command + "][client:" + channel.remoteAddress() + "]");
            channel.close();
            return;
        }

        com.google.protobuf.GeneratedMessageLite request = (GeneratedMessageLite) data.getParamObj();

        GeneratedMessageLite response = null;
        SocketUser socketUser = socketUserManager.getUserByChannel(channel);
        if (command == CatCmd.CMD_JOIN_CAT_ROOM && socketUser == null) {
            long userId = ((CatPortoBuf.JoinCatRoomRequest)request).getUserId();
            socketUser = new SocketUser(channel,userId);
            socketUserManager.addUser(socketUser);
        }

        // 绑定用户到当前执行线程上下文中, 方便后面使用
        ServerContext.getContext().setUser(socketUser);

        try {
            response = processor.handle(request);
            if (null != response) {
                MessageHolderProtobuf messageHolder = new MessageHolderProtobuf(command, response);
                //socketUser.send(messageHolder);
                channel.writeAndFlush(messageHolder);
                ReferenceCountUtil.release(messageHolder);
            }
        } catch (Exception exception) {
            logger.error("[handleRequest][handle_error][command:" + command + "][" + exception.getMessage() + "]", exception);
        } catch (Throwable throwable) {
            logger.error("[handleRequest][handle_error][command:" + command + "][" + throwable.getMessage() + "]", throwable);
        } finally {
            // 清除本次上下文
            ServerContext.clear();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exceptionCaught start");
        super.exceptionCaught(ctx, cause);
        logger.error("exceptionCaught cause:"+cause.getMessage());
        logger.info("exceptionCaught start");
    }
}