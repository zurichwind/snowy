package cn.zurish.snow.core.filter.server;

import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.annotations.SPI;
import cn.zurish.snow.core.filter.ServerFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端日志过滤器
 * 2023/12/30 11:48
 */
@SPI("before")
@Slf4j
public class ServerLogFilterImpl implements ServerFilter {


    @Override
    public void doFilter(RpcInvocation rpcInvocation) {
        log.info(rpcInvocation.getAttachments().get("client_app_name") + " do invoke -----> " +
                rpcInvocation.getTargetServiceName() + "#" + rpcInvocation.getTargetMethod());
    }
}