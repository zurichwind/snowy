package cn.zurish.snow.core.common.event.listener;

import cn.zurish.snow.core.common.event.RpcDestoryEvent;
import cn.zurish.snow.core.registry.URL;

import java.util.Iterator;

import static cn.zurish.snow.core.common.cache.CommonServerCache.PROVIDER_URL_SET;
import static cn.zurish.snow.core.common.cache.CommonServerCache.REGISTRY_SERVICE;

/**
 * 2023/12/29 14:02
 */
public class ServiceDestoryListener implements RpcListener<RpcDestoryEvent> {

    @Override
    public void callBack(Object t) {
        Iterator<URL> urlIterator = PROVIDER_URL_SET.iterator();
        while (urlIterator.hasNext()) {
            URL url = urlIterator.next();
            urlIterator.remove();
            REGISTRY_SERVICE.unRegister(url);
        }
    }
}