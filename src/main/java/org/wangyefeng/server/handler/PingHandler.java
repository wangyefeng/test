package org.wangyefeng.server.handler;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.Protocol;


public class PingHandler extends AbstractNoMessageHandle {

    private static final Logger log = LoggerFactory.getLogger(PingHandler.class);

    private static  final ProtoBufMessage PONG = new ProtoBufMessage<>(Protocol.PONG);

    @Override
    public void handle0(Channel channel) {
        log.info("Received a ping message from client.");
        channel.writeAndFlush(PONG);
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.PING;
    }
}
