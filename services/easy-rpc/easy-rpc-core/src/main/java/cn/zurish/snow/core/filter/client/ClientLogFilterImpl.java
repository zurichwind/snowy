package cn.zurish.snow.core.filter.client;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.filter.ClientFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static cn.zurish.snow.core.common.cache.CommonClientCache.CLIENT_CONFIG;

/**
 * 客户端日志记录过滤链路
 * 2023/12/30 11:33
 */
@Slf4j
public class ClientLogFilterImpl implements ClientFilter {

    @Override
    public void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation) {
        rpcInvocation.getAttachments().put("client_app_name", CLIENT_CONFIG.getApplicationName());
        log.info(rpcInvocation.getAttachments().get("client_app_name") + " do invoke -----> " +
                rpcInvocation.getTargetServiceName() + "#" + rpcInvocation.getTargetMethod());
    }
}
