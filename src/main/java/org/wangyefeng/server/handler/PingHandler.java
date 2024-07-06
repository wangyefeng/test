package org.wangyefeng.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.protocol.C2SProtocol;
import org.wangyefeng.protocol.S2CProtocol;


public class PingHandler extends AbstractNoMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(PingHandler.class);

    private static  final ByteBuf PONG;

    static {
        ByteBuf byteBuf = Unpooled.buffer(8);
        byteBuf.writeInt(4);
        byteBuf.writeInt(S2CProtocol.PONG.getCode());
        PONG = Unpooled.unreleasableBuffer(byteBuf);
    }

    @Override
    public void handle0(Channel channel) {
        log.info("Received a ping message from client.");
        channel.writeAndFlush(PONG.duplicate());// 回应PONG消息
    }

    @Override
    public C2SProtocol getProtocol() {
        return C2SProtocol.PING;
    }
}
