package org.wyf.server;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import org.wyf.ProtoBufMessage;
import org.wyf.Protocol;

import java.util.HashMap;
import java.util.Map;

public interface Handler<T extends Message> {

    Map<Integer, Handler> handlers = new HashMap<>();

    static void register(Handler handler) {
        handlers.put(handler.getProtocol().getCode(), handler);
    }

    static Handler getHandler(int code) {
        return handlers.get(code);
    }

    void handle(Channel channel, ProtoBufMessage<T> message);

    Protocol getProtocol();

}
