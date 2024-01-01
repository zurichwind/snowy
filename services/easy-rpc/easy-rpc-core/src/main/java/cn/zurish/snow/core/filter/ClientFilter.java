package cn.zurish.snow.core.filter;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.RpcInvocation;

import java.util.List;

/**
 * 客户端过滤器
 * 2023/12/29 14:25
 */
public interface ClientFilter extends Filter {

    /**
     * 执行过滤链
     *
     * @param src
     * @param rpcInvocation
     * @return
     */
    void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation);
}