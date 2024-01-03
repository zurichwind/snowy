package cn.zurish.snow.core.rpc.handler;

import cn.zurish.snow.core.rpc.provider.ServiceProvider;
import cn.zurish.snow.core.rpc.provider.ServiceProviderImpl;
import cn.zurish.snow.rpc.common.entity.RpcRequest;
import cn.zurish.snow.rpc.common.entity.RpcResponse;
import cn.zurish.snow.rpc.common.enumeration.ResponseCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 进行过程调用的处理器
 * 2024/1/1 23:12
 */
@Slf4j
public class RequestHandler {

    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest request) {
        Object service = serviceProvider.getServiceProvider(request.getInterfaceName());
        return invokeTargetMethod(request, service);
    }

    private Object invokeTargetMethod(RpcRequest request, Object service) {
        Object result;
        try{
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            result = method.invoke(service, (Object) request.getParamTypes());
            log.info("服务:{} 成功调用方法:{}",  request.getParameters());
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, request.getRequestId());
        }
        return result;

    }

}
