/**
 * $Id: SocketUserManager.java 2309 2014-04-10 08:08:43Z lida.zhang $
 */
package cn.lida.netty.server;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * socket user 管理器
 * 主要封装 netty channel
 * 
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketUserManager {
	private  ConcurrentMap<Channel, SocketUser> channelWitchSocketUserMap;
	private  ConcurrentMap<Long, SocketUser> userIdWitchSocketUserMap;
	
	public SocketUserManager(){
		channelWitchSocketUserMap = new ConcurrentHashMap<Channel, SocketUser>();
		userIdWitchSocketUserMap = new ConcurrentHashMap<Long, SocketUser>();
	};
	
	public boolean contains(Channel channel){
		return channelWitchSocketUserMap.containsKey(channel);
	}
	
	public SocketUser getUserByChannel(Channel channel){
		return channelWitchSocketUserMap.get(channel);
	}
	public SocketUser getUserByUid(long gamePlayerId){
		return userIdWitchSocketUserMap.get(gamePlayerId);
	}
	
	public void addUser(SocketUser user){
		if(user.getUserId() < 1)
			throw new IllegalArgumentException("add socket user userId is 0");
		
		channelWitchSocketUserMap.put(user.getChannel(), user);
		userIdWitchSocketUserMap.put(user.getUserId(), user);
	}
	
	public ConcurrentMap<Channel, SocketUser> getUsersByChannel() {
		return channelWitchSocketUserMap;
	}

	/**
	 * 获取当前在线的所有玩家channel
	 * @return
	 */
	public List<SocketUser> getOnlineSocketUsers(){
		return new ArrayList<SocketUser>(userIdWitchSocketUserMap.values());
	}
	
	public void removeUserByUid(long gamePlayerId){
		SocketUser removed = userIdWitchSocketUserMap.remove(gamePlayerId);
		if (null != removed) {
			channelWitchSocketUserMap.remove(removed.getChannel());
		}
	}
	
	public void removeUserByChannel(Channel channel){
		SocketUser user = channelWitchSocketUserMap.remove(channel);
		if(null != user){
			userIdWitchSocketUserMap.remove(user.getUserId());
		}
	}

	public long getChannelWitchSocketUserSize(){
		return channelWitchSocketUserMap.size();
	}

    public long getUserIdWitchSocketUserSize(){
        return userIdWitchSocketUserMap.size();
    }

	public ConcurrentMap<Channel, SocketUser> getChannelWitchSocketUserMap() {
		return channelWitchSocketUserMap;
	}

	public ConcurrentMap<Long, SocketUser> getUserIdWitchSocketUserMap() {
		return userIdWitchSocketUserMap;
	}
	
}
