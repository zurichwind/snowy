package cn.zurish.snow.test.exception;

public class SnowException extends RuntimeException{




    public SnowException(String message) {
        super(message);
    }

    public SnowException(String message, Throwable cause) {
       super(message, cause);
    }



}
