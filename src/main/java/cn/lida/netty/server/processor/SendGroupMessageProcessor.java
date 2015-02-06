package cn.lida.netty.server.processor;

import cn.lida.netty.protocol.CatPortoBuf;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-21
 * Email:zhanglida@huoqiu.cn
 */
public class SendGroupMessageProcessor implements ISocketProcessor<CatPortoBuf.SendGroupMessageRequest,CatPortoBuf.SendGroupMessageRequest> {

    @Override
    public CatPortoBuf.SendGroupMessageRequest handle(CatPortoBuf.SendGroupMessageRequest request) {
        return null;
    }
}
