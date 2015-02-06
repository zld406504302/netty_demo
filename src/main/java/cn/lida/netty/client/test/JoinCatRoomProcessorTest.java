package cn.lida.netty.client.test;

import cn.lida.netty.client.SocketClientMain;
import cn.lida.netty.client.TimeCostStat;
import cn.lida.netty.protocol.CatCmd;
import cn.lida.netty.protocol.CatPortoBuf;
import cn.lida.netty.protocol.MessageHolderProtobuf;
import cn.lida.netty.util.BeanFactory;
import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-23
 * Email:zhanglida@huoqiu.cn
 */
public class JoinCatRoomProcessorTest {

    public static void test(Channel channel) {

        int totalMsgCount = 2000 ;
        final AtomicInteger counter = new AtomicInteger(0);
        TimeCostStat timeCostStat = TimeCostStat.getInstance();
        timeCostStat.setSingleMsgSize(1).setStatIntervalSecond(1000).setTotalMsgCount(totalMsgCount-1).start();

        for (int i = 1; i < totalMsgCount; i++) {
            CatPortoBuf.JoinCatRoomRequest.Builder builder = CatPortoBuf.JoinCatRoomRequest.newBuilder().setUserId(i).setUserName("test"+i);
            MessageHolderProtobuf messageHolderProtobuf = new MessageHolderProtobuf(CatCmd.CMD_JOIN_CAT_ROOM,builder.build());
            channel.writeAndFlush(messageHolderProtobuf);
            timeCostStat.incrSendCount();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SocketClientMain socketClientMain = BeanFactory.getInstance(SocketClientMain.class);
        Channel channel = socketClientMain.start();

        JoinCatRoomProcessorTest.test(channel);


        Thread.sleep(1000000);
    }

}
