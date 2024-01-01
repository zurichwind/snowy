package cn.zurish.snow.core.filter.server;

import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.annotations.SPI;
import cn.zurish.snow.core.common.utils.CommonUtil;
import cn.zurish.snow.core.filter.ServerFilter;
import cn.zurish.snow.core.server.ServiceWrapper;

import static cn.zurish.snow.core.common.cache.CommonServerCache.PROVIDER_SERVICE_WRAPPER_MAP;

/**
 * 简单版本的token校验
 * 2023/12/30 12:03
 */
@SPI("before")
public class ServerTokenFilterImpl implements ServerFilter {

    @Override
    public void doFilter(RpcInvocation rpcInvocation) {
        String token = String.valueOf(rpcInvocation.getAttachments().get("serviceToken"));
        if (!PROVIDER_SERVICE_WRAPPER_MAP.containsKey(rpcInvocation.getTargetServiceName())) {
            return;
        }
        ServiceWrapper serviceWrapper = PROVIDER_SERVICE_WRAPPER_MAP.get(rpcInvocation.getTargetServiceName());
        String matchToken = String.valueOf(serviceWrapper.getServiceToken());
        if (CommonUtil.isEmpty(matchToken)) return;
        if (CommonUtil.isNotEmpty(token) && token.equals(matchToken)) return;
        throw new RuntimeException("token is " + token + " , verify result is false!");
    }
}
