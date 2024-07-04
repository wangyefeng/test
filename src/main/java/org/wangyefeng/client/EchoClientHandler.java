package org.wangyefeng.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.Protocol;
import org.wangyefeng.proto.Common;

import java.util.concurrent.TimeUnit;

public class EchoClientHandler extends SimpleChannelInboundHandler<ProtoBufMessage<?>> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().writeAndFlush(new ProtoBufMessage<>(Protocol.LOGIN, Common.PbInt.newBuilder().setVal(100).build()));
        ctx.executor().scheduleAtFixedRate(() -> ctx.channel().writeAndFlush(new ProtoBufMessage<>(Protocol.PING)), 5, 5, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBufMessage message) {
        System.out.println("Received message: " + message.getCode() + " " + message.getMessage());
    }
}
