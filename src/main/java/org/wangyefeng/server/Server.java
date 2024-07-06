package org.wangyefeng.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wangyefeng.ProtobufCodec;


public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private static Server instance = new Server();

    public static Server getInstance() {
        return instance;
    }

    private Channel channel;

    private boolean isRunning = false;

    private Server() {
    }

    public void start(int port) {
        if (isRunning) {
            throw new IllegalStateException("Server is already running");
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 用于接收客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 用于处理客户端连接
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ServerHandler handler = new ServerHandler();
            bootstrap.group(bossGroup, workerGroup)
                     .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         public void initChannel(SocketChannel ch) {
                             ChannelPipeline pipeline = ch.pipeline();
                             pipeline.addLast(new ReadTimeoutHandler(20));
                             pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 10, 0, 4, 0, 4));
                             pipeline.addLast(new ProtobufCodec());
                             pipeline.addLast(handler);
                         }
                     });
            bootstrap.option(ChannelOption.SO_BACKLOG, 200);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true); // 允许端口复用

            bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);// 禁用tcp保活机制，自定义ping包检测tcp链接是否正常
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);// 禁用Nagle算法
            bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024 * 128); // 设置接收缓冲区大小
            bootstrap.childOption(ChannelOption.SO_SNDBUF, 1024 * 128); // 设置发送缓冲区大小
            // 绑定端口并启动服务器
            ChannelFuture future = bootstrap.bind(port).sync();
            channel = future.channel();
            isRunning = true;
            log.info("EchoServer started and listening on port {}", port);
            channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> {
                isRunning = false;
                log.warn("EchoServer stopped");
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            });
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            throw new RuntimeException(e);
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
