package cn.zurish.snow.core.proxy;

import cn.zurish.snow.core.client.RpcReferenceWrapper;

/**
 * 代理工厂接口
 * 2023/12/29 14:27
 */
public interface ProxyFactory {


    <T> T getProxy(final RpcReferenceWrapper<T> rpcReferenceWrapper) throws Throwable;
}