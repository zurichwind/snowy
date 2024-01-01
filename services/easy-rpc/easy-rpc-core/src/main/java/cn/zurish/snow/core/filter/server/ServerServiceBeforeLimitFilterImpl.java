package cn.zurish.snow.core.filter.server;

import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.ServerServiceSemaphoreWrapper;
import cn.zurish.snow.core.common.annotations.SPI;
import cn.zurish.snow.core.common.exception.MaxServiceLimitRequestException;
import cn.zurish.snow.core.filter.ServerFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

import static cn.zurish.snow.core.common.cache.CommonServerCache.SERVER_SERVICE_SEMAPHORE_MAP;

/**
 * 服务端方法限流过滤器
 * 2023/12/30 11:50
 */
@SPI("before")
@Slf4j
public class ServerServiceBeforeLimitFilterImpl implements ServerFilter {
    @Override
    public void doFilter(RpcInvocation rpcInvocation) {
        String serviceName = rpcInvocation.getTargetServiceName();
        if (!SERVER_SERVICE_SEMAPHORE_MAP.containsKey(serviceName)) {
            return;
        }
        ServerServiceSemaphoreWrapper serverServiceSemaphoreWrapper = SERVER_SERVICE_SEMAPHORE_MAP.get(serviceName);
        //从缓存中提取semaphore对象
        Semaphore semaphore = serverServiceSemaphoreWrapper.getSemaphore();
        boolean tryResult = semaphore.tryAcquire();
        if (!tryResult) {
            log.error("[ServerServiceBeforeLimitFilterImpl] {}'s max request is {},reject now", rpcInvocation.getTargetServiceName(), serverServiceSemaphoreWrapper.getMaxNums());
            MaxServiceLimitRequestException rpcException = new MaxServiceLimitRequestException(rpcInvocation);
            rpcInvocation.setE(rpcException);
            throw rpcException;
        }
    }
}