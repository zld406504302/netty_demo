package cn.lida.netty.server.module;

import cn.lida.netty.entity.UserInfoEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:聊天室
 * Author:zhanglida
 * Date:15-1-22
 * Email:zhanglida@huoqiu.cn
 */
public class CatRoom {

    Map<RoomID,Map<Long,UserInfoEntity>> roomMap = new ConcurrentHashMap<RoomID,Map<Long,UserInfoEntity>>();


    CatRoom(){
        for (RoomID roomID : RoomID.values()) {
            roomMap.put(roomID,new HashMap<Long,UserInfoEntity>());
        }
    }

    public void join(RoomID roomID , UserInfoEntity userInfoEntity){
        roomMap.get(roomID).put(userInfoEntity.getId(),userInfoEntity);
    }

    public Map<Long,UserInfoEntity> getUsers(RoomID roomID ){
        return roomMap.get(roomID);
    }

    public Map<RoomID,Map<Long,UserInfoEntity>> getRoomMap(){
        return roomMap;
    }

    public List<Integer> getRoomIds(){
        RoomID[] roomIds = RoomID.values();
        List<Integer> ids = new ArrayList<Integer>();
        for(RoomID roomId : roomIds){
            ids.add(roomId.getId());
        }

        return ids ;
    }


    public static void main(String[] args){
        CatRoom room = new CatRoom();
        System.out.println(room.getRoomIds().toArray());

    }

    public enum RoomID
    {
        one(1),
        tow(2),
        three(3);
        int id ;

        RoomID(int id){
            this.id = id ;
        }
        public int getId() {
            return id;
        }
    }

}
