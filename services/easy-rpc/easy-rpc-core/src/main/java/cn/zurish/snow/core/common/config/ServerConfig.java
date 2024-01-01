package cn.zurish.snow.core.common.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务端配置类
 * 2023/12/30 10:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerConfig {

    private Integer port;

    private String registerAddr;

    private String registerType;

    private String applicationName;

    /**
     * 服务端序列化方式 example: hessian2,kryo,jdk,fastjson
     */
    private String serverSerialize;

    /**
     * 服务端业务线程数目
     */
    private Integer serverBizThreadNums;

    /**
     * 服务端接收队列的大小
     */
    private Integer serverQueueSize;

    /**
     * 限制服务端最大所能接受的数据包体积
     */
    private Integer maxServerRequestData;

    /**
     * 服务端最大连接数
     */
    private Integer maxConnections;

}
