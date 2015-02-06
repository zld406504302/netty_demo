/**
 * $Id: GameContext.java 2157 2014-03-31 10:23:01Z lida.zhang $
 */
package cn.lida.netty.server;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class ServerContext {
	private final static ThreadLocal<ServerContext> ctx = new ThreadLocal<ServerContext>();
	
	private SocketUser user;

	public static ServerContext getContext() {
		ServerContext context = ctx.get();
		if (context == null) {
			context = new ServerContext();
			ctx.set(context);
		}
		return context;
	}
	
	public static void clear() {
		ctx.remove();
	}
	
	public SocketUser getUser() {
		return user;
	}

	public void setUser(SocketUser user) {
		this.user = user;
	}
}
