package cn.zurish.snow.core.filter.server;

import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.ServerServiceSemaphoreWrapper;
import cn.zurish.snow.core.common.annotations.SPI;
import cn.zurish.snow.core.filter.ServerFilter;

import static cn.zurish.snow.core.common.cache.CommonServerCache.SERVER_SERVICE_SEMAPHORE_MAP;

/**
 * 服务端用于释放semaphore对象
 * 2023/12/30 11:49
 */
@SPI("after")
public class ServerServiceAfterLimitFilterImpl implements ServerFilter {

    @Override
    public void doFilter(RpcInvocation rpcInvocation) {
        String serviceName = rpcInvocation.getTargetServiceName();
        if (!SERVER_SERVICE_SEMAPHORE_MAP.containsKey(serviceName)) {
            return;
        }
        ServerServiceSemaphoreWrapper serverServiceSemaphoreWrapper = SERVER_SERVICE_SEMAPHORE_MAP.get(serviceName);
        serverServiceSemaphoreWrapper.getSemaphore().release();
    }
}

