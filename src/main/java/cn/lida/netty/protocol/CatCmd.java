/**
 * $Id$
 * Copyright(C) 2010-2016 wme.inc. All rights reserved.
 */
package cn.lida.netty.protocol;

public class CatCmd {
	// Ping
	public static final int CMD_PING = 1;

    public static final int CMD_TEST = 2;

    // 加入聊天室
    public static final int CMD_JOIN_CAT_ROOM = 100 ;

    //发松私聊消息
    public static final int CMD_SEND_PRIVATE_MESSAGE = 101 ;

    //发送群组消息
    public static final int CMD_SEND_GROUP_MESSAGE = 102 ;
	
	
}
