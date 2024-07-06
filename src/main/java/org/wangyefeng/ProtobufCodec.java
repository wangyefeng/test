package org.wangyefeng;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.wangyefeng.protocol.C2SProtocol;
import org.wangyefeng.util.Assert;

import java.util.List;

/**
 * gate与中心服务器以及跨服的编码器
 *
 * @author 王叶峰
 * @date 2021年6月25日
 */
public class ProtobufCodec extends ByteToMessageCodec<ProtoBufMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoBufMessage msg, ByteBuf out) throws Exception {
        if (msg.getMessage() != null) {
            out.writeInt(0);
            out.writeInt(msg.getCode());
            ByteBufOutputStream outputStream = new ByteBufOutputStream(out);
            msg.getMessage().writeTo(outputStream);
            out.setInt(0, out.readableBytes() - 4);
        } else {
            out.writeInt(4);
            out.writeInt(msg.getCode());
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> out) throws Exception {
        int code = msg.readInt();
        Assert.isTrue(C2SProtocol.match(code), "Invalid code: " + code);
        int length = msg.readableBytes();
		if (length > 0) {
			ByteBufInputStream inputStream = new ByteBufInputStream(msg);
			out.add(new ProtoBufMessage<>(code, (Message) C2SProtocol.getParser(code).parseFrom(inputStream)));
		} else {
			out.add(new ProtoBufMessage<>(code));
		}
    }

}
