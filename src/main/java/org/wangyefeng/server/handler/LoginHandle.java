package org.wangyefeng.server.handler;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.Protocol;
import org.wangyefeng.proto.Common;

public class LoginHandle implements Handler<Common.PbInt> {


    private static final Logger log = LoggerFactory.getLogger(LoginHandle.class);

    public LoginHandle() {
    }

    @Override
    public void handle(Channel channel, ProtoBufMessage<Common.PbInt> message) {
        log.info("LoginHandle: {}", message.getMessage());
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.LOGIN;
    }
}
