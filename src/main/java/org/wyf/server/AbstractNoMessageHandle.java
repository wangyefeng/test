package org.wyf.server;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import org.wyf.ProtoBufMessage;

public abstract class AbstractNoMessageHandle implements Handler<Message> {

    public AbstractNoMessageHandle() {
    }

    @Override
    public void handle(Channel channel, ProtoBufMessage<Message> message) {
        handle0(channel);
    }

    abstract public void handle0(Channel channel);
}
