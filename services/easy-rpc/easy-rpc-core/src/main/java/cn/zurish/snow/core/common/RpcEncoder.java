package cn.zurish.snow.core.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import static cn.zurish.snow.core.common.constants.RpcConstants.DEFAULT_DECODE_CHAR;

/**
 * RPC 编码器
 * 2023/12/29 13:07
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol msg, ByteBuf out) throws Exception {
        out.writeShort(msg.getMagicNumber());
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());
        out.writeBytes(DEFAULT_DECODE_CHAR.getBytes());
    }
}
