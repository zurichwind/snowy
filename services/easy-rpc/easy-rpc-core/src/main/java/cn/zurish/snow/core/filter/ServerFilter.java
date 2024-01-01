package cn.zurish.snow.core.filter;

import cn.zurish.snow.core.common.RpcInvocation;

/**
 * 服务端过滤器
 * 2023/12/29 14:24
 */
public interface ServerFilter extends Filter {

    /**
     * 执行核心过滤逻辑
     *
     * @param rpcInvocation
     */
    void doFilter(RpcInvocation rpcInvocation);
}