package cn.zurish.snow.rpc.common.exception;

/**
 * 序列化异常
 * 2024/1/1 18:34
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}