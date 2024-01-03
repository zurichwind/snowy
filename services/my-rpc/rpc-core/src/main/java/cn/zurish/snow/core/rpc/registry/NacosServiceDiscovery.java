package cn.zurish.snow.core.rpc.registry;

import cn.zurish.snow.core.rpc.loadbalancer.LoadBalancer;
import cn.zurish.snow.core.rpc.loadbalancer.RandomLoadBalancer;
import cn.zurish.snow.rpc.common.enumeration.RpcError;
import cn.zurish.snow.rpc.common.exception.SnowException;
import cn.zurish.snow.rpc.common.util.NacosUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 2024/1/1 23:21
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if(instances.isEmpty()) {
                log.error("找不到对应的服务: " + serviceName);
                throw new SnowException(RpcError.SERVICE_NOT_FOUND);
            }
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }

}
