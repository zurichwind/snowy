package cn.zurish.snow.core.common.event.listener;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.event.RpcNodeUpdateEvent;
import cn.zurish.snow.core.common.event.RpcUpdateEvent;
import cn.zurish.snow.core.common.event.data.ProviderNodeInfo;
import cn.zurish.snow.core.registry.URL;

import java.util.List;

import static cn.zurish.snow.core.common.cache.CommonClientCache.CONNECT_MAP;
import static cn.zurish.snow.core.common.cache.CommonClientCache.ROUTER;

/**
 * 2023/12/29 14:03
 */
public class ProviderNodeUpdateListener implements RpcListener<RpcNodeUpdateEvent> {
    @Override
    public void callBack(Object o) {
        ProviderNodeInfo providerNodeInfo = ((ProviderNodeInfo) o);
        List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(providerNodeInfo.getServiceName());
        for(ChannelFutureWrapper channelFutureWrapper : channelFutureWrappers) {
            String address = channelFutureWrapper.getHost()+":"+channelFutureWrapper.getPort();
            if(address.equals(providerNodeInfo.getAddress())){
                //修改权重
                channelFutureWrapper.setWeight(providerNodeInfo.getWeight());
                URL url = new URL();
                url.setServiceName(providerNodeInfo.getServiceName());
                //更新权重
                ROUTER.updateWeight(url);
                break;
            }
        }
    }
}
