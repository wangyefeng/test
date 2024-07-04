package org.wangyefeng;

import com.google.protobuf.Message;

public class ProtoBufMessage<T extends Message> {

    private int code;

    private T message;

    public ProtoBufMessage(Protocol protocol) {
        this(protocol.getCode());
    }

    public ProtoBufMessage(Protocol protocol, T message) {
        this(protocol.getCode(), message);
    }

    public ProtoBufMessage(int code) {
        this(code, null);
    }

    public ProtoBufMessage(int code, T message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public T getMessage() {
        return message;
    }
}
