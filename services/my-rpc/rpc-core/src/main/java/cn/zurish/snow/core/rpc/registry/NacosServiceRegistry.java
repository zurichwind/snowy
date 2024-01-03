package cn.zurish.snow.core.rpc.registry;

import cn.zurish.snow.rpc.common.enumeration.RpcError;
import cn.zurish.snow.rpc.common.exception.SnowException;
import cn.zurish.snow.rpc.common.util.NacosUtil;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 服务注册中心
 * 2024/1/1 23:21
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {


    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new SnowException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
