package cn.zurish.snow.core.rpc.transport;

import cn.zurish.snow.core.rpc.serializer.CommonSerializer;
import cn.zurish.snow.rpc.common.entity.RpcRequest;

/**
 * 客户端类通用接口
 * 2024/1/1 23:26
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest request);
}
