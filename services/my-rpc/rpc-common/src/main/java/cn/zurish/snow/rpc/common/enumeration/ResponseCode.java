package cn.zurish.snow.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 方法调用的响应状态码
 * 2024/1/1 18:31
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    CLASS_NOT_FOUND(500, "未找到指定类");

    private final int code;
    private final String message;

}