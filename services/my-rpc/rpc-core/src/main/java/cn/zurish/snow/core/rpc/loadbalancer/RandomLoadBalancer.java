package cn.zurish.snow.core.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * 2024/1/1 23:15
 */
public class RandomLoadBalancer implements LoadBalancer{
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}