package cn.zurish.snow.core.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务包装类
 * 2023/12/30 13:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceWrapper {
    /**
     * 对外暴露的具体服务对象
     */
    private Object serviceBean;

    /**
     * 具体暴露服务的分组
     */
    private String group = "default";

    /**
     * 整个应用的token校验
     */
    private String serviceToken = "";

    /**
     * 限流策略
     */
    private Integer limit = -1;

    /**
     * 服务权重
     */
    private Integer weight = 100;

    public ServiceWrapper(Object serviceObj) {
        this.serviceBean = serviceObj;
    }

    public ServiceWrapper(Object serviceObj, String group) {
        this.serviceBean = serviceObj;
        this.group = group;
    }
}
