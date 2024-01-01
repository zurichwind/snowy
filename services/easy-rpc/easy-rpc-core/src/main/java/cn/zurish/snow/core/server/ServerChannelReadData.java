package cn.zurish.snow.core.server;

import cn.zurish.snow.core.common.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

/**
 * 2023/12/30 13:39
 */
@Getter
public class ServerChannelReadData {

    private RpcProtocol rpcProtocol;

    private ChannelHandlerContext channelHandlerContext;

    public void setRpcProtocol(RpcProtocol rpcProtocol) {
        this.rpcProtocol = rpcProtocol;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}