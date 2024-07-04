package org.wangyefeng.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.server.handler.Handler;

import java.net.SocketException;

public class EchoServerHandler extends SimpleChannelInboundHandler<ProtoBufMessage<?>> {

    private static final Logger log = LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("Channel active: {}", ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBufMessage message) {
        Handler<?> handler = Handler.getHandler(message.getCode());
        handler.handle(ctx.channel(), message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof SocketException) {
            log.info("Socket exception {} channel: {}", cause.getMessage(), ctx.channel());
        } else if (cause instanceof ReadTimeoutException) {
            log.info("Read timeout: {}", ctx.channel());
        } else {
            log.error("Exception caught in channel: {}", ctx.channel(), cause);
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("Channel inactive: {}", ctx.channel());
    }
}
