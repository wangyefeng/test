package org.wangyefeng.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.server.handler.Handler;
import org.wangyefeng.server.handler.LoginHandle;
import org.wangyefeng.server.handler.PingHandler;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        registerHandler();
        EchoServer server = EchoServer.getInstance();
        log.info("Server is starting...");
        server.start(8888);
    }

    public static void registerHandler() {
        Handler.register(new LoginHandle());
        Handler.register(new PingHandler());
    }
}
