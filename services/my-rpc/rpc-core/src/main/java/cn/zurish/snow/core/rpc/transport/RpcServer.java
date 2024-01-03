package cn.zurish.snow.core.rpc.transport;

import cn.zurish.snow.core.rpc.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 * 2024/1/1 23:26
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);
}
