package cn.lida.netty.server.processor;

import cn.lida.netty.entity.UserInfoEntity;
import cn.lida.netty.protocol.CatPortoBuf;
import cn.lida.netty.server.module.CatRoom;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-21
 * Email:zhanglida@huoqiu.cn
 */
public class JoinCatRoomProcessor implements ISocketProcessor<CatPortoBuf.JoinCatRoomRequest,CatPortoBuf.JoinCatRoomResponse> {

    @Inject
    CatRoom catRoom ;
    int counter = 0;
    @Override
    public CatPortoBuf.JoinCatRoomResponse handle(CatPortoBuf.JoinCatRoomRequest request) {

        long userId = request.getUserId();
        String userName = request.getUserName();
        UserInfoEntity userInfoEntity = new UserInfoEntity(userId,userName);
        catRoom.join(CatRoom.RoomID.one,userInfoEntity);
        Map<Long,UserInfoEntity> userInfoEntityMap =  catRoom.getUsers(CatRoom.RoomID.one);
        List<CatPortoBuf.UserInfo> users = getUserInfos(CatRoom.RoomID.one,userInfoEntityMap);

        CatPortoBuf.JoinCatRoomResponse.Builder builder = CatPortoBuf.JoinCatRoomResponse.newBuilder();
        builder.addAllUsers(users);
        System.out.println("userId:"+userId+" userName:"+userName);
        return builder.build();

    }



    List<CatPortoBuf.UserInfo> getUserInfos(CatRoom.RoomID roomID ,Map<Long,UserInfoEntity> userInfoEntityMap){
        List<CatPortoBuf.UserInfo> userInfos = new ArrayList<CatPortoBuf.UserInfo>();
        for(Map.Entry<Long,UserInfoEntity> infoEntityEntry : userInfoEntityMap.entrySet()){
            CatPortoBuf.UserInfo.Builder builder = CatPortoBuf.UserInfo.newBuilder();
            builder.setGroupId(roomID.getId()).setUserId(infoEntityEntry.getKey()).setUserName(infoEntityEntry.getValue().getUserName());
            userInfos.add(builder.build());
        }

        return userInfos;
    }
}
