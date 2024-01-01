package cn.zurish.snow.core.common.exception;

import cn.zurish.snow.core.common.RpcInvocation;

/**
 * 服务端限流异常
 * 2023/12/29 13:00
 */
public class MaxServiceLimitRequestException extends RpcException{

    public MaxServiceLimitRequestException(RpcInvocation rpcInvocation) {
        super(rpcInvocation);
    }
}