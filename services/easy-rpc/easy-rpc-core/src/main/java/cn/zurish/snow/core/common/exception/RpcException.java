package cn.zurish.snow.core.common.exception;

import cn.zurish.snow.core.common.RpcInvocation;

/**
 * 2023/12/29 12:16
 */
public class RpcException extends RuntimeException{


    private RpcInvocation rpcInvocation;

    public RpcException(RpcInvocation rpcInvocation) {
        this.rpcInvocation = rpcInvocation;
    }

    public RpcInvocation getRpcInvocation() {
        return rpcInvocation;
    }

    public void setRpcInvocation(RpcInvocation rpcInvocation) {
        this.rpcInvocation = rpcInvocation;
    }



}
