package cn.zurish.snow.core.filter.client;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.filter.ClientFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端模块的过滤链设计
 * 2023/12/30 11:32
 */
public class ClientFilterChain {

    private static final List<ClientFilter> clientFilterList = new ArrayList<>();

    public void addClientFilter(ClientFilter clientFilter) {
        clientFilterList.add(clientFilter);
    }

    public void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation) {
        for (ClientFilter iClientFilter : clientFilterList) {
            iClientFilter.doFilter(src, rpcInvocation);
        }
    }

}