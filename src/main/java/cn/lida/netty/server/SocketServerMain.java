/**
 * $Id: PokerGameServer.java 3365 2014-06-26 10:58:15Z lida.zhang $
 * Copyright(C) 2010-2016 mbkele.com.cn. All rights reserved.
 */
package cn.lida.netty.server;

import cn.lida.netty.util.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketServerMain {
	private static Logger logger = LoggerFactory.getLogger(SocketServerMain.class);
	
	
	public static void main(String[] args){
        try {
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>>>>>>>>>>>>socket start fail <<<<<<<<<<");
        }
    }
	
	public static void start() throws InterruptedException {

		SocketServer server = BeanFactory.getInstance(SocketServer.class);
        SocketServerSetting serverSetting = BeanFactory.getInstance(SocketServerSetting.class);

		server.setServerSetting(serverSetting);

		server.start();
		
		logger.info(">>>>>>>>>>>>>>>server started<<<<<<<<<<<<<<<<");
    }
	
}
