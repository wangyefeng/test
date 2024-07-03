package org.wyf.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.wyf.ProtoBufMessage;

public class EchoServerHandler extends SimpleChannelInboundHandler<ProtoBufMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBufMessage message) throws Exception {
        Handler.getHandler(message.getCode()).handle(ctx.channel(), message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
