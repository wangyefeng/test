package org.wyf.server;

import io.netty.channel.Channel;
import org.wyf.ProtoBufMessage;
import org.wyf.Protocol;
import org.wyf.proto.Common;

public class LoginHandle implements Handler<Common.PbInt> {

    public LoginHandle() {
    }

    @Override
    public void handle(Channel channel, ProtoBufMessage<Common.PbInt> message) {
        System.out.println("LoginHandle: " + message.getMessage().getVal());
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.LOGIN;
    }
}
