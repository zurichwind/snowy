package cn.zurish.snow.core.filter.client;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.utils.CommonUtil;
import cn.zurish.snow.core.filter.ClientFilter;

import java.util.List;

/**
 * ip直连过滤器
 * 2023/12/30 11:35
 */
public class DirectInvokeFilterImpl implements ClientFilter {
    @Override
    public void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation) {
        String url = (String) rpcInvocation.getAttachments().get("url");
        if (CommonUtil.isEmpty(url)) return;

        src.removeIf(channelFutureWrapper -> !(channelFutureWrapper.getHost() + ":" + channelFutureWrapper.getPort()).equals(url));
        if (CommonUtil.isEmptyList(src)) {
            throw new RuntimeException("no match provider url for " + url);
        }
    }
}
