package org.wangyefeng;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.wangyefeng.protocol.C2SProtocol;
import org.wangyefeng.util.Assert;

import java.util.List;

public class ProtobufDecoder extends ByteToMessageDecoder {

    /**
     * Creates a new instance.
     */
    public ProtobufDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out)
            throws Exception {
        int code = msg.readInt();
        Assert.isTrue(C2SProtocol.match(code), "Invalid code: " + code);
        final int length = msg.readableBytes();
        if (length > 0) {
            ByteBufInputStream inputStream = new ByteBufInputStream(msg);
            out.add(new ProtoBufMessage<>(code, (Message) C2SProtocol.getParser(code).parseFrom(inputStream)));
        } else {
            out.add(new ProtoBufMessage<>(code));
        }
    }
}
