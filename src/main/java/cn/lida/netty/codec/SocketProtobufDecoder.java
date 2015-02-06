package cn.lida.netty.codec;

import com.google.protobuf.MessageLite;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-2-2
 * Email:zhanglida@huoqiu.cn
 */
public class SocketProtobufDecoder extends ProtobufDecoder {

    public SocketProtobufDecoder(MessageLite prototype) {
        super(prototype);
    }

}
