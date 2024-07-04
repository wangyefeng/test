package org.wangyefeng.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.wangyefeng.protocol.C2SProtocol;
import org.wangyefeng.ProtoBufMessage;
import org.wangyefeng.proto.Common;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ProtoBufMessage<?>> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().writeAndFlush(new ProtoBufMessage<>(C2SProtocol.LOGIN, Common.PbInt.newBuilder().setVal(100).build()));
        ctx.executor().scheduleAtFixedRate(() -> ctx.channel().writeAndFlush(new ProtoBufMessage<>(C2SProtocol.PING)), 5, 5, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBufMessage message) {
        System.out.println("Received message: " + message.getCode() + " " + message.getMessage());
    }
}
