/**
 * $Id: SerializaUtil.java 49 2013-10-17 05:17:47Z lida.zhang $
 * Copyright(C) 2010-2016 xgame.com. All rights reserved.
 */
package cn.lida.netty.codec;

import java.io.*;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SerializaUtil {

	public static byte[] serializeObj(Object obj){		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			 oos = new ObjectOutputStream(bos);
			 oos.writeObject(obj);
			 return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("serializeObj error", e);
		}		
	}
	
	public static Object deSerializeObj(byte[] array){
		ByteArrayInputStream bis = new ByteArrayInputStream(array);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			return ois.readObject();		
		} catch (Exception e) {
			throw new RuntimeException("deSerializeObj error", e);
		} 	
	}
	
	
}
