package cn.zurish.snow.rpc.common.util;

import cn.zurish.snow.rpc.common.entity.RpcRequest;
import cn.zurish.snow.rpc.common.entity.RpcResponse;
import cn.zurish.snow.rpc.common.enumeration.ResponseCode;
import cn.zurish.snow.rpc.common.enumeration.RpcError;
import cn.zurish.snow.rpc.common.exception.SnowException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 检查响应与请求
 * 2024/1/1 18:36
 */
@Slf4j
@NoArgsConstructor
public class RpcMessageChecker {

    public static final String INTERFACE_NAME = "interfaceName";

    public static void check(RpcRequest request, RpcResponse<?> response) {
        if (response == null) {
            log.error("调用服务失败, serviceName: {}", request.getInterfaceName());
            throw new SnowException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + request.getInterfaceName());
        }

        if (!request.getRequestId().equals(response.getRequestId())) {
            throw new SnowException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME +":" + request.getInterfaceName());
        }

        if (response.getStatusCode() == null || !response.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
            log.error("调用服务失败, serviceName:{}, RpcResponse:{}", request.getInterfaceName(), response);
            throw new SnowException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + request.getInterfaceName());
        }
    }
}
