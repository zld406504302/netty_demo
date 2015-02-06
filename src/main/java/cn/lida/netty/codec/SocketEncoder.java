package cn.lida.netty.codec;

import cn.lida.netty.protocol.MessageHolderProtobuf;
import com.google.protobuf.GeneratedMessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
@ChannelHandler.Sharable
public class SocketEncoder extends MessageToByteEncoder<MessageHolderProtobuf> {

	private static final int HEADER_SIZE = 4;
	private static final int COMMAD_SIZE = 4;

	protected static byte[] zip(byte[] buff) {
		if (buff.length == 0) {
			return buff;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(bos);
		try {
			deflaterOutputStream.write(buff);
			deflaterOutputStream.finish();
			deflaterOutputStream.close();
		} catch (IOException e) {
		}
		return bos.toByteArray();
	}

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageHolderProtobuf msg, ByteBuf out) throws Exception {
        if (null == msg)
            return ;

        int command = msg.getCommand();
        GeneratedMessageLite paramObj = msg.getParamObj();

        byte[] buff = ProtobufSerializaUtil.serializeObj(paramObj);

        out.writeInt(buff.length);
        out.writeInt(command);
        out.writeBytes(buff);

        //out.retain();
    }
}