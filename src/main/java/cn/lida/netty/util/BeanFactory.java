package cn.lida.netty.util;

/**
 * $Id: GuiceFactory.java 49 2013-10-17 05:17:47Z lida.zhang $
 * Copyright(C) 2010-2016 mbkele.com.cn. All rights reserved.
 */

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class BeanFactory {
	private static Injector injector = Guice.createInjector(new AbstractModule() {
		@Override
		protected void configure() {
			// bind(PingProcessor.class).toInstance(new PingProcessor());
		}
	});

	private static Map<Object, Object> instanceMap = new HashMap<Object, Object>();

	public static <T> T getInstance(Class<T> clazz) {
		Object exist = instanceMap.get(clazz);
		if (null == exist) {
			exist = injector.getInstance(clazz);
			instanceMap.put(clazz, exist);
		}

		return clazz.cast(exist);
	}
}