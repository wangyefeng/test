package org.wangyefeng.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.server.handler.Handler;

public class EchoServerHandler extends SimpleChannelInboundHandler<ProtoBufMessage> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBufMessage message) throws Exception {
        Handler.getHandler(message.getCode()).handle(ctx.channel(), message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught in channel: {}", ctx.channel(), cause);
        ctx.close();
    }
}
