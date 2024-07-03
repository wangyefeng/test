package org.wyf;

import com.google.protobuf.Message;

public class ProtoBufMessage<T extends Message> {

    private int code;

    private T message;

    public ProtoBufMessage(int code) {
        this.code = code;
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
