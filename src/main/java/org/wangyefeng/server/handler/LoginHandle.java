package org.wangyefeng.server.handler;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.protocol.C2SProtocol;
import org.wangyefeng.proto.Common;
import org.wangyefeng.protocol.S2CProtocol;

public class LoginHandle implements Handler<Common.PbInt> {


    private static final Logger log = LoggerFactory.getLogger(LoginHandle.class);

    @Override
    public void handle(Channel channel, Common.PbInt message) {
        log.info("玩家: {} 登录", message.getVal());
        channel.writeAndFlush(new ProtoBufMessage<>(S2CProtocol.LOGIN));// 通知客户端登录成功
    }

    @Override
    public C2SProtocol getProtocol() {
        return C2SProtocol.LOGIN;
    }
}
