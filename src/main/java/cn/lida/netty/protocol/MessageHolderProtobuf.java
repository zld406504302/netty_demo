/**
 * $Id: MessageHolderProtobuf.java 1485 2014-01-16 08:38:35Z lida.zhang $
 * Copyright(C) 2010-2016 mbkele.com.cn. All rights reserved.
 */
package cn.lida.netty.protocol;

import com.google.protobuf.GeneratedMessageLite;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class MessageHolderProtobuf {
	private int command;
	private GeneratedMessageLite paramObj;
	
	public MessageHolderProtobuf(int command, GeneratedMessageLite paramObj){
		this.command = command;
		this.paramObj = paramObj;
	}

	/**
	 * 直接构造要发送 的 消息对象
	 * @param command
	 * @param paramObj
	 * @return
	 */
	public static MessageHolderProtobuf build(int command, GeneratedMessageLite paramObj){
		return new MessageHolderProtobuf(command, paramObj);
	}
	
	public int getCommand() {
		return command;
	}

	@Override
	public String toString() {
		return "MessageHolder [command=" + command + ", paramObj=" + paramObj +"]";
	}

	/**
	 * @return the paramObj
	 */
	public GeneratedMessageLite getParamObj() {
		return paramObj;
	}
	
	public int hashCode(){
		return command ;
	}
	
	public boolean equals(MessageHolderProtobuf essageHolderProtobuf) {
		if (essageHolderProtobuf == null || this == null)
			return false;
		
		if(this.command == essageHolderProtobuf.getCommand()){
			return this.getParamObj().equals(essageHolderProtobuf.getParamObj());
		}else{
			return false;
		}
	}
}
