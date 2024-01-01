package cn.zurish.snow.core.filter.server;

import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.filter.ServerFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端模块的前置过滤链设计
 * 2023/12/30 11:43
 */
public class ServerBeforeFilterChain {
    private static final List<ServerFilter> serverFilters = new ArrayList<>();

    public void addServerFilter(ServerFilter iServerFilter) {
        serverFilters.add(iServerFilter);
    }

    public void doFilter(RpcInvocation rpcInvocation) {
        for (ServerFilter serverFilter : serverFilters) {
            serverFilter.doFilter(rpcInvocation);
        }
    }
}
