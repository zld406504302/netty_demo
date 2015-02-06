package cn.lida.netty.server.processor;

import cn.lida.netty.protocol.CatPortoBuf;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-27
 * Email:zhanglida@huoqiu.cn
 */
public class TestProcessor implements ISocketProcessor<CatPortoBuf.TestRequest,CatPortoBuf.TestResponse> {
    @Override
    public CatPortoBuf.TestResponse handle(CatPortoBuf.TestRequest request) {
        return CatPortoBuf.TestResponse.newBuilder().setCommand(CatPortoBuf.Command.CMD_TEST).setContent(request.getContent()).build();
    }
}
