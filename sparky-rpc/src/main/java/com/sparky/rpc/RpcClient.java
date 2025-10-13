package com.sparky.rpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.sparky.core.SparkyLogger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Клієнт для RPC з'єднань.
 *
 * @author Андрій Будильников
 */
public class RpcClient {
    private static final SparkyLogger logger = SparkyLogger.getLogger(RpcClient.class);
    
    private final String host;
    private final int port;
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final ConcurrentHashMap<String, CompletableFuture<RpcMessage>> pendingRequests = new ConcurrentHashMap<>();
    
    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Підключається до сервера.
     */
    public void connect() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new RpcClientHandler(RpcClient.this));
                    }
                });
        
        channel = bootstrap.connect(host, port).sync().channel();
    }
    
    /**
     * Викликає віддалений метод.
     */
    public CompletableFuture<RpcMessage> call(String method, RpcMessage params) {
        String id = String.valueOf(nextId.getAndIncrement());
        RpcMessage message = new RpcMessage(method, params.getParams(), id);
        
        CompletableFuture<RpcMessage> future = new CompletableFuture<>();
        pendingRequests.put(id, future);
        
        channel.writeAndFlush(message.toJson());
        
        return future;
    }
    
    /**
     * Обробляє відповідь від сервера.
     */
    public void handleResponse(RpcMessage response) {
        String id = response.getId();
        CompletableFuture<RpcMessage> future = pendingRequests.remove(id);
        if (future != null) {
            future.complete(response);
        }
    }
    
    /**
     * Закриває з'єднання.
     */
    public void close() {
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }
    
    // Package-private method for handler to access pending requests
    ConcurrentHashMap<String, CompletableFuture<RpcMessage>> getPendingRequests() {
        return pendingRequests;
    }
}