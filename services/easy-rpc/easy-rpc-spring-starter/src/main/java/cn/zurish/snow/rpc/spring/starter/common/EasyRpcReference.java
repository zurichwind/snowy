package cn.zurish.snow.rpc.spring.starter.common;

import java.lang.annotation.*;

/**
 * 2023/12/31 19:20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyRpcReference {
    //直连url
    String url() default "";
    //服务分组
    String group() default "default";
    //服务的令牌校验
    String serviceToken() default "";
    //服务调用的超时时间
    int timeOut() default 3000;
    //重试次数
    int retry() default 1;
    //是否异步调用
    boolean async() default false;
}