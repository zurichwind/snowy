package cn.zurish.snow.core.common.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *客户端配置类
 * 2023/12/30 10:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientConfig {

    private String registerAddr;

    private String registerType;

    private String applicationName;

    /**
     * 代理类型 example: jdk,javassist
     */
    private String proxyType;

    /**
     * 负载均衡策略 example:random,rotate
     */
    private String routerStrategy;

    /**
     * 客户端序列化方式 example: hessian2,kryo,jdk,fastjson
     */
    private String clientSerialize;

    /**
     * 客户端发数据的超时时间
     */
    private Integer timeOut;

    /**
     * 客户端最大响应数据体积
     */
    private Integer maxServerRespDataSize;
}
