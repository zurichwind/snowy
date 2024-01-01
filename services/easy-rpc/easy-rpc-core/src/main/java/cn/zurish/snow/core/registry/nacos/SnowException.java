package cn.zurish.snow.core.registry.nacos;

/**
 * 2023/12/31 20:07
 */
public class SnowException extends RuntimeException{
    public SnowException( String detail) {
        super(detail);
    }

    public SnowException(String message, Throwable cause) {
        super(message, cause);
    }


}
