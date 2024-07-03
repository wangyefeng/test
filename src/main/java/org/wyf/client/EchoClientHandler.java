package org.wyf.client;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.wyf.ProtoBufMessage;
import org.wyf.Protocol;
import org.wyf.proto.Common;

import java.util.concurrent.TimeUnit;

public class EchoClientHandler extends SimpleChannelInboundHandler<ProtoBufMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().writeAndFlush(new ProtoBufMessage(1, Common.PbInt.newBuilder().setVal(100).build()));
        ctx.executor().scheduleAtFixedRate(() -> {
            ctx.channel().writeAndFlush(new ProtoBufMessage(Protocol.PING.getCode()));
        }, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBufMessage message) throws Exception {
        System.out.println("Received message: " + message.getCode() + " " + message.getMessage());
    }
}
