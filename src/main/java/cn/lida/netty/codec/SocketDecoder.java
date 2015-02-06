/**
 * $Id: SocketDecoder.java 3017 2014-06-03 02:26:03Z lida.zhang $
 */
package cn.lida.netty.codec;

import cn.lida.netty.protocol.CatProtocolFacade;
import cn.lida.netty.protocol.MessageHolderProtobuf;
import com.google.protobuf.GeneratedMessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * @author lida.zhang@huoqiu.cn
 * @version 1.0
 * @since 1.0
 */
public class SocketDecoder extends ByteToMessageDecoder {
	private static Logger logger = LoggerFactory.getLogger(SocketDecoder.class);

    protected static byte[] unzip(byte[] buff) {
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
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        int readableBytes = byteBuf.readableBytes();
        if(readableBytes <= 4) {
            return ;
        }

        int command = byteBuf.readInt();
        byte [] dst = new byte[readableBytes-4];
        byteBuf.readBytes(dst);

        GeneratedMessageLite request = CatProtocolFacade.getRequest(command);
        Class requestClass = null ;
        MessageHolderProtobuf holder = null ;
        try{
            requestClass = request.getClass();
            GeneratedMessageLite paramObject = ProtobufSerializaUtil.deSerializeObj(dst, requestClass);
            holder = new MessageHolderProtobuf(command, paramObject);
            out.add(holder);
            //byteBuf.readSlice(readableBytes);
            System.out.println("byteBuf"+byteBuf);

        }catch(Exception e){
            logger.error("##################Commond[" + command + "] GeneratedMessageLite[" + (requestClass == null ? "null" : requestClass) + "]", e);
            return ;
        }finally {
            //byteBuf.release();
        }



    }
}