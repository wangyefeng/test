package org.wangyefeng;

public enum S2CProtocol implements Protocol {

    PONG(0),

    ;

    private final int code;

    S2CProtocol(int code) {
        if (code < 0) {
            throw new IllegalArgumentException("code must be non-negative");
        }
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
