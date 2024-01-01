package cn.zurish.snow.core.common.event.listener;

import cn.zurish.snow.core.client.ConnectionHandler;
import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.common.event.RpcUpdateEvent;
import cn.zurish.snow.core.common.event.data.ProviderNodeInfo;
import cn.zurish.snow.core.common.event.data.URLChangeWrapper;
import cn.zurish.snow.core.registry.URL;
import cn.zurish.snow.core.router.Selector;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cn.zurish.snow.core.common.cache.CommonClientCache.CONNECT_MAP;
import static cn.zurish.snow.core.common.cache.CommonClientCache.ROUTER;

/**
 * 2023/12/29 14:02
 */
@Slf4j
public class ServiceUpdateListener implements RpcListener<RpcUpdateEvent> {
    @Override
    public void callBack(Object o) {
        //获取到字节点的数据信息
        URLChangeWrapper urlChangeWrapper = (URLChangeWrapper) o;
        List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(urlChangeWrapper.getServiceName());

        List<String> matchProviderUrl = urlChangeWrapper.getProviderUrl();
        Set<String> finalUrl = new HashSet<>();
        List<ChannelFutureWrapper> finalChannelFutureWrappers = new ArrayList<>();
        for (ChannelFutureWrapper channelFutureWrapper : channelFutureWrappers) {
            String oldServerAddress = channelFutureWrapper.getHost() + ":" + channelFutureWrapper.getPort();
            //如果老的url没有，说明已经被移除了
            if (!matchProviderUrl.contains(oldServerAddress)) {
                continue;
            }
            finalChannelFutureWrappers.add(channelFutureWrapper);
            finalUrl.add(oldServerAddress);
        }

        List<ChannelFutureWrapper> newChannelFutureWrapper = new ArrayList<>();
        for (String newProviderUrl : matchProviderUrl){
            if (!finalUrl.contains(newProviderUrl)) {
                ChannelFutureWrapper channelFutureWrapper = new ChannelFutureWrapper();
                String host = newProviderUrl.split(":")[0];
                Integer port = Integer.valueOf(newProviderUrl.split(":")[1]);
                channelFutureWrapper.setPort(port);
                channelFutureWrapper.setHost(host);
                String urlStr = urlChangeWrapper.getNodeDataUrl().get(newProviderUrl);
                ProviderNodeInfo providerNodeInfo = URL.buildUrlFromUrlStr(urlStr);
                channelFutureWrapper.setWeight(providerNodeInfo.getWeight());
                channelFutureWrapper.setGroup(providerNodeInfo.getGroup());
                ChannelFuture channelFuture = null;
                try {
                    channelFuture = ConnectionHandler.createChannelFuture(host, port);
                    log.debug("channelFuture reconnect,server is {}:{}",host,port);
                    channelFutureWrapper.setChannelFuture(channelFuture);
                    newChannelFutureWrapper.add(channelFutureWrapper);
                    finalUrl.add(newProviderUrl);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            finalChannelFutureWrappers.addAll(newChannelFutureWrapper);
            //最终在这里更新服务cache
            CONNECT_MAP.put(urlChangeWrapper.getServiceName(), finalChannelFutureWrappers);
            Selector selector = new Selector();
            selector.setProviderServiceName(urlChangeWrapper.getServiceName());
            ROUTER.refreshRouterArr(selector);
        }
    }
}
