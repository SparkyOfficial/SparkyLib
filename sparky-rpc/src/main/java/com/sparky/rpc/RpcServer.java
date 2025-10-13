package com.sparky.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.sparky.core.SparkyLogger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Сервер для RPC з'єднань.
 *
 * @author Андрій Будильников
 */
public class RpcServer {
    private static final SparkyLogger logger = SparkyLogger.getLogger(RpcServer.class);
    
    private final int port;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    private final Map<String, Function<RpcMessage, RpcMessage>> handlers = new ConcurrentHashMap<>();
    
    public RpcServer(int port) {
        this.port = port;
    }
    
    /**
     * Реєструє обробник для методу.
     */
    public void registerHandler(String method, Function<RpcMessage, RpcMessage> handler) {
        handlers.put(method, handler);
    }
    
    /**
     * Запускає сервер.
     */
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new RpcServerHandler(RpcServer.this));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        
        channel = bootstrap.bind(port).sync().channel();
        logger.info("RPC server started on port " + port);
    }
    
    /**
     * Зупиняє сервер.
     */
    public void stop() {
        if (channel != null) {
            channel.close();
        }
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
    
    // Package-private method for handler to access handlers
    Map<String, Function<RpcMessage, RpcMessage>> getHandlers() {
        return handlers;
    }
}