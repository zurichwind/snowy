package cn.zurish.snow.core.client;

import cn.zurish.snow.core.common.utils.CommonUtil;
import cn.zurish.snow.core.proxy.ProxyFactory;

import static cn.zurish.snow.core.common.cache.CommonClientCache.CLIENT_CONFIG;

/**
 * rpc远程调用类
 * 2023/12/30 10:56
 */
public class RpcReference {
    public ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    /**
     * 根据接口类型获取代理对象
     */
    public <T> T get(RpcReferenceWrapper<T> rpcReferenceWrapper) throws Throwable {
        initGlobalRpcReferenceConfig(rpcReferenceWrapper);
        return proxyFactory.getProxy(rpcReferenceWrapper);
    }

    private void initGlobalRpcReferenceConfig(RpcReferenceWrapper<?> rpcReferenceWrapper) {
        if (CommonUtil.isEmpty(rpcReferenceWrapper.getTimeOut())) {
            rpcReferenceWrapper.setTimeOut(CLIENT_CONFIG.getTimeOut());
        }
    }
}
