package cn.zurish.snow.core.router;

import cn.zurish.snow.core.common.ChannelFutureWrapper;
import cn.zurish.snow.core.registry.URL;



/**
 * 路由接口
 * 2023/12/29 14:23
 */
public interface Router {

    /**
     * 刷新路由数组
     *
     * @param selector
     */
    void refreshRouterArr(Selector selector);

    /**
     * 获取到请求的连接通道
     *
     * @param channelFutureWrappers
     * @return
     */
    ChannelFutureWrapper select(ChannelFutureWrapper[] channelFutureWrappers);

    /**
     * 更新权重信息
     *
     * @param url
     */
    void updateWeight(URL url);
}