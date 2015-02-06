package cn.lida.netty.server.processor;

import com.google.protobuf.GeneratedMessageLite;

/**
 *
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public interface ISocketProcessor<T extends GeneratedMessageLite, M extends GeneratedMessageLite> {
	public M handle(T request);
}
