/**
 * $Id: ProcessorProtoEnum.java 3042 2014-06-05 02:17:52Z lida.zhang $
 * Copyright(C) 2010-2016 mbkele.com.cn. All rights reserved.
 */
package cn.lida.netty.protocol;

import cn.lida.netty.server.processor.*;
import cn.lida.netty.util.BeanFactory;
import com.google.protobuf.GeneratedMessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public enum CatProtocolFacade {

    PROTO_TEST(CatCmd.CMD_TEST, CatPortoBuf.TestRequest.class, TestProcessor.class, CatPortoBuf.TestResponse.class),
    PROTO_JOIN_CAT_ROOM(CatCmd.CMD_JOIN_CAT_ROOM, CatPortoBuf.JoinCatRoomRequest.class, JoinCatRoomProcessor.class, CatPortoBuf.JoinCatRoomResponse.class),
    PROTO_SEND_PRIVATE_MESSAGE(CatCmd.CMD_SEND_PRIVATE_MESSAGE, CatPortoBuf.SendPrivateMessageRequest.class, SendPrivateMessageProcessor.class, CatPortoBuf.SendPrivateMessageRequest.class),
    PROTO_SEND_GROUP_MESSAGE(CatCmd.CMD_SEND_GROUP_MESSAGE, CatPortoBuf.SendGroupMessageRequest.class, SendGroupMessageProcessor.class, CatPortoBuf.SendGroupMessageRequest.class),
    ;
	private static Logger logger = LoggerFactory.getLogger(CatProtocolFacade.class);
	
	private int command;
	private Class<?> socketRequest;
	private Class<?> requestProcessor;
	private Class<?> socketResponse;
	
	private static Map<Integer, ISocketProcessor> processorMap = new HashMap<Integer, ISocketProcessor>();
	private static Map<Integer, GeneratedMessageLite> responseMap = new HashMap<Integer, GeneratedMessageLite>();
	private static Map<Integer, GeneratedMessageLite> requestMap = new HashMap<Integer, GeneratedMessageLite>();
	static{
		logger.info("init protocol enum start");
		
		for(CatProtocolFacade elem : CatProtocolFacade.values()){
			int command = elem.getCommand();
			ISocketProcessor processor = (ISocketProcessor) BeanFactory.getInstance(elem.requestProcessor);
			if (processor != null)
				processorMap.put(elem.getCommand(), processor);
			
			GeneratedMessageLite response;
			try {
				response = (GeneratedMessageLite) elem.getSocketResponse().getMethod("getDefaultInstance", null).invoke(null, null);
				responseMap.put(elem.getCommand(), response);
				GeneratedMessageLite request = (GeneratedMessageLite) elem.getSocketRequest().getMethod("getDefaultInstance", null).invoke(null, null);
				requestMap.put(elem.getCommand(), request);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("", e);
			} catch (SecurityException e) {
				throw new RuntimeException("", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("", e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("", e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("", e);
			} 
		}
		logger.info("init protocol enum finish");
	}
	
	public static GeneratedMessageLite getRequest(int command){

		return requestMap.get(command);
	}

    public static GeneratedMessageLite getResponse(int command){
        return responseMap.get(command);
    }
	public static ISocketProcessor getProcessor(int command){
		return processorMap.get(command);
	}
	public static GeneratedMessageLite getDefaultResponse(int command){
		return responseMap.get(command);
	}
	private CatProtocolFacade(int command, Class<?> request, Class<?> processor, Class<?> response){
		this.command = command;
		this.socketRequest = request;
		this.requestProcessor = processor;
		this.socketResponse = response;
	}

	public int getCommand() {
		return command;
	}

	public Class<?> getSocketRequest() {
		return socketRequest;
	}

	public Class<?> getRequestProcessor() {
		return requestProcessor;
	}
	public Class<?> getSocketResponse(){
		return socketResponse;
	}
}
