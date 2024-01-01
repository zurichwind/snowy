package cn.zurish.snow.core.router;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.registry.URL;

import java.util.List;

import static cn.zurish.snow.core.common.cache.CommonClientCache.*;

/**
 * 2023/12/31 14:46
 */
public class RotateRouterImpl implements Router {


    @Override
    public void refreshRouterArr(Selector selector) {
        List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(selector.getProviderServiceName());
        ChannelFutureWrapper[] arr = new ChannelFutureWrapper[channelFutureWrappers.size()];
        for (int i=0;i<channelFutureWrappers.size();i++) {
            arr[i]=channelFutureWrappers.get(i);
        }
        SERVICE_ROUTER_MAP.put(selector.getProviderServiceName(),arr);
    }

    @Override
    public ChannelFutureWrapper select(ChannelFutureWrapper[] channelFutureWrappers) {
        return CHANNEL_FUTURE_POLLING_REF.getChannelFutureWrapper(channelFutureWrappers);
    }

    @Override
    public void updateWeight(URL url) {

    }
}
