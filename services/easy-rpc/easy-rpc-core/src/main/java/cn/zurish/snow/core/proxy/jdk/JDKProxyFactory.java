package cn.zurish.snow.core.proxy.jdk;

import cn.zurish.snow.core.client.RpcReferenceWrapper;
import cn.zurish.snow.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * 2023/12/31 14:52
 */
public class JDKProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(RpcReferenceWrapper<T> rpcReferenceWrapper) throws Throwable {
        return (T) Proxy.newProxyInstance(rpcReferenceWrapper.getAimClass().getClassLoader(), new Class[]{rpcReferenceWrapper.getAimClass()},
                new JDKClientInvocationHandler(rpcReferenceWrapper));
    }

}