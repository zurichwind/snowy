package cn.zurish.snow.rpc.common.exception;

import cn.zurish.snow.rpc.common.enumeration.RpcError;

/**
 * 自定义业务异常
 * 2024/1/1 18:33
 */
public class SnowException extends RuntimeException{
    public SnowException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public SnowException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnowException(RpcError error) {
        super(error.getMessage());
    }

}
