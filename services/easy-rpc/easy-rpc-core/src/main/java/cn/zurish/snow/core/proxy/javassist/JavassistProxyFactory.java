package cn.zurish.snow.core.proxy.javassist;


import cn.zurish.snow.core.client.RpcReferenceWrapper;
import cn.zurish.snow.core.proxy.ProxyFactory;

/**
 *
 */
public class JavassistProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(RpcReferenceWrapper<T> rpcReferenceWrapper) throws Throwable {
        return (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                rpcReferenceWrapper.getAimClass(), new JavassistInvocationHandler(rpcReferenceWrapper));
    }
}
