package org.wangyefeng.server.handler;

import com.google.protobuf.Message;
import io.netty.channel.Channel;

public abstract class AbstractNoMessageHandle implements Handler<Message> {

    public AbstractNoMessageHandle() {
    }

    public void handle(Channel channel, Message message) {
        handle0(channel);
    }

    abstract public void handle0(Channel channel);
}
