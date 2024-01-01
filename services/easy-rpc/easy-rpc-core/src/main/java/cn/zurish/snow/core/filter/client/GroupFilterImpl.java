package cn.zurish.snow.core.filter.client;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.utils.CommonUtil;
import cn.zurish.snow.core.filter.ClientFilter;

import java.util.List;

/**
 * 2023/12/30 11:37
 */
public class GroupFilterImpl implements ClientFilter {
    @Override
    public void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation) {
        String group = String.valueOf(rpcInvocation.getAttachments().get("group"));
        src.removeIf(channelFutureWrapper -> !channelFutureWrapper.getGroup().equals(group));
        if (CommonUtil.isEmptyList(src)) {
            throw new RuntimeException("no provider match for group " + group);
        }
    }
}
