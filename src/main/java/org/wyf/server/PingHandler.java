package org.wyf.server;

import io.netty.channel.Channel;
import org.wyf.ProtoBufMessage;
import org.wyf.Protocol;


public class PingHandler extends AbstractNoMessageHandle {

    @Override
    public void handle0(Channel channel) {
        System.out.println("ping");
        channel.writeAndFlush(new ProtoBufMessage<>(Protocol.PONG.getCode()));
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.PING;
    }
}
