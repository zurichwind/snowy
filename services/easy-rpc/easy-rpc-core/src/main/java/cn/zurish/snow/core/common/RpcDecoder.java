package cn.zurish.snow.core.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static cn.zurish.snow.core.common.constants.RpcConstants.MAGIC_NUMBER;

/**
 * RPC 解码器
 * 2023/12/29 13:07
 */
public class RpcDecoder extends ByteToMessageDecoder {

    /**
     * 协议开头部分的标准长度
     * */

    public final static int BASE_LENGTH = 2 + 4;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes() >= BASE_LENGTH) {
            if (byteBuf.readShort() != MAGIC_NUMBER) {
                // 不是魔数开头，说明是非法客户端发来的数据包
                ctx.close();
                return;
            }

            int length = byteBuf.readInt();
            //说明剩余的数据包不是完整的，这里需要重置下读索引
            if (byteBuf.readableBytes() < length) {
                // 数据包有异常
                ctx.close();
                return;
            }

            byte[] data = new byte[length];
            byteBuf.readBytes(data);
            RpcProtocol rpcProtocol = new RpcProtocol(data);

            out.add(rpcProtocol);
        }
    }
}
