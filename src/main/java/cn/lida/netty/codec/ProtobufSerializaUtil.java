package cn.lida.netty.codec;
import com.google.protobuf.GeneratedMessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class ProtobufSerializaUtil {

	private static Logger logger = LoggerFactory.getLogger(ProtobufSerializaUtil.class);
	public  static  <T extends GeneratedMessageLite> byte[] serializeObj(T message){
		return message.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends GeneratedMessageLite> T deSerializeObj(byte[] bys, Class<T> cl){
		
		try {
			T tt = (T) cl.getMethod("getDefaultInstance", null).invoke(null, null);
			return (T) cl.getMethod("parseFrom", byte[].class).invoke(tt, bys);			
		} catch (IllegalAccessException e) {
			throw new RuntimeException("", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("", e);
		} catch (SecurityException e) {
			throw new RuntimeException("", e);
		} catch (InvocationTargetException e) {
			    logger.debug("ProtobufSerializaUtil.deSerializeObj args[cl]:"+cl);
			throw new RuntimeException("", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("", e);
		}
			
	}
}
