package com.sparky.rpc;

import com.sparky.core.SparkyLogger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.function.Function;

/**
 * Обробник повідомлень для RPC сервера.
 *
 * @author Андрій Будильников
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<String> {
    private static final SparkyLogger logger = SparkyLogger.getLogger(RpcServerHandler.class);
    
    private final RpcServer server;
    
    public RpcServerHandler(RpcServer server) {
        this.server = server;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            RpcMessage request = RpcMessage.fromJson(msg);
            String method = request.getMethod();
            
            Function<RpcMessage, RpcMessage> handler = server.getHandlers().get(method);
            if (handler != null) {
                try {
                    RpcMessage response = handler.apply(request);
                    response.setId(request.getId());
                    ctx.writeAndFlush(response.toJson());
                } catch (Exception e) {
                    logger.error("Error handling RPC method: " + method, e);
                    // Send error response
                    RpcMessage errorResponse = new RpcMessage("error", null, request.getId());
                    ctx.writeAndFlush(errorResponse.toJson());
                }
            } else {
                logger.warn("No handler found for RPC method: " + method);
                // Send error response
                RpcMessage errorResponse = new RpcMessage("method_not_found", null, request.getId());
                ctx.writeAndFlush(errorResponse.toJson());
            }
        } catch (Exception e) {
            logger.error("Failed to parse RPC request: " + msg, e);
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("RPC server handler exception", cause);
        ctx.close();
    }
}