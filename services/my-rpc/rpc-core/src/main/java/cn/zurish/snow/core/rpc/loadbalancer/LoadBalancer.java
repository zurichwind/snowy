package cn.zurish.snow.core.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 2024/1/1 23:15
 */
public interface LoadBalancer {

    Instance select(List<Instance> instances);
}
