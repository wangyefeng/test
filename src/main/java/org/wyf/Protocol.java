package org.wyf;

import com.google.protobuf.Parser;
import org.wyf.proto.Common;

import java.util.HashMap;
import java.util.Map;

public enum Protocol {

    PING(0),

    LOGIN(1, Common.PbInt.parser()),

    PONG(2),

    ;

    private final int code;

    private final Parser parser;

    private static final Map<Integer, Protocol> PROTOCOLS = new HashMap<>();

    Protocol(int code) {
        this(code, null);
    }

    Protocol(int code, Parser parser) {
        if (code < 0) {
            throw new IllegalArgumentException("code must be non-negative");
        }
        this.code = code;
        this.parser = parser;
    }

    public int getCode() {
        return code;
    }

    public Parser<?> getParser() {
        return parser;
    }

    static {
        for (Protocol protocol : Protocol.values()) {
            if (PROTOCOLS.containsKey(protocol.code)) {
                throw new IllegalStateException("duplicate code: " + protocol.code + " for " + protocol + " and " + PROTOCOLS.get(protocol.code));
            }
            PROTOCOLS.put(protocol.code, protocol);
        }
    }

    public static boolean isIllegal(int code) {
        return !PROTOCOLS.containsKey(code);
    }

    public static Parser getParser(int code) {
        return PROTOCOLS.get(code).getParser();
    }
}
