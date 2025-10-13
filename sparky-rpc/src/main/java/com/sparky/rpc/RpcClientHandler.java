package com.sparky.rpc;

import com.sparky.core.SparkyLogger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Обробник повідомлень для RPC клієнта.
 *
 * @author Андрій Будильников
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<String> {
    private static final SparkyLogger logger = SparkyLogger.getLogger(RpcClientHandler.class);
    
    private final RpcClient client;
    
    public RpcClientHandler(RpcClient client) {
        this.client = client;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            RpcMessage response = RpcMessage.fromJson(msg);
            client.handleResponse(response);
        } catch (Exception e) {
            logger.error("Failed to parse RPC response: " + msg, e);
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("RPC client handler exception", cause);
        ctx.close();
    }
}