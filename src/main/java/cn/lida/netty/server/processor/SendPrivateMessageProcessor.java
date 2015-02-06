package cn.lida.netty.server.processor;

import cn.lida.netty.protocol.CatPortoBuf;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-21
 * Email:zhanglida@huoqiu.cn
 */
public class SendPrivateMessageProcessor implements ISocketProcessor<CatPortoBuf.JoinCatRoomRequest,CatPortoBuf.JoinCatRoomRequest> {

    @Override
    public CatPortoBuf.JoinCatRoomRequest handle(CatPortoBuf.JoinCatRoomRequest request) {
        return null;
    }

}
