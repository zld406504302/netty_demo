package cn.lida.netty.client;

import cn.lida.netty.util.BeanFactory;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-22
 * Email:zhanglida@huoqiu.cn
 */
public class SocketClientMain {
    private static Logger logger = LoggerFactory.getLogger(SocketClientMain.class);

    public Channel start() throws InterruptedException {
        logger.info(">>>>>>>>>>>>>>>client ready<<<<<<<<<<<<<<<<");
        SocketClient client = BeanFactory.getInstance(SocketClient.class);
        SocketClientSetting clientSetting = BeanFactory.getInstance(SocketClientSetting.class);
        client.setServerSetting(clientSetting).init();

        logger.info(">>>>>>>>>>>>>>>client started<<<<<<<<<<<<<<<<");
        return client.getChannel();
    }
}
