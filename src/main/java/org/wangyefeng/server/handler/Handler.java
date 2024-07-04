package org.wangyefeng.server.handler;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.Protocol;

import java.util.HashMap;
import java.util.Map;

public interface Handler<T extends Message> {

    Map<Integer, Handler<? extends Message>> handlers = new HashMap<>();

    static void register(Handler<? extends Message> handler) {
        if (handlers.containsKey(handler.getProtocol().getCode())) {
            throw new IllegalArgumentException("Duplicate protocol:" + handler.getProtocol());
        }
        handlers.put(handler.getProtocol().getCode(), handler);
    }

    static Handler<? extends Message> getHandler(int code) {
        return handlers.get(code);
    }

    void handle(Channel channel, ProtoBufMessage<T> message);

    Protocol getProtocol();

}
