package cn.zurish.snow.core.common.event;

import cn.zurish.snow.core.common.event.listener.RpcListener;
import cn.zurish.snow.core.common.utils.CommonUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.zurish.snow.core.common.cache.CommonClientCache.EXTENSION_LOADER;
import static cn.zurish.snow.core.spi.ExtensionLoader.EXTENSION_LOADER_CLASS_CACHE;

/**
 * 2023/12/29 13:42
 */
public class RpcListenerLoader {

    private static final List<RpcListener<?>> rpcListenerList = new ArrayList<>();

    private static final ExecutorService eventThreadPool = Executors.newFixedThreadPool(2);

    public static void registerListener(RpcListener<?> rpcListener) {
        rpcListenerList.add(rpcListener);
    }

    public void init() {
        try {
            EXTENSION_LOADER.loadExtension(RpcListener.class);
            LinkedHashMap<String, Class<?>> listenerMap = EXTENSION_LOADER_CLASS_CACHE.get(RpcListener.class.getName());
            for (Map.Entry<String, Class<?>> listenerEntry : listenerMap.entrySet()) {
                String key = listenerEntry.getKey();
                Class<?> listener = listenerEntry.getValue();
                registerListener((RpcListener<?>) listener.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException("registerListener unKnow,error is ", e);
        }
    }

    public static void sendEvent(RpcEvent rpcEvent) {
        if(CommonUtil.isEmptyList(rpcListenerList)){
            return;
        }
        for (RpcListener<?> rpcListener : rpcListenerList) {
            Class<?> type = getInterfaceT(rpcListener);
            if(type != null && type.equals(rpcEvent.getClass())){
                eventThreadPool.execute(()->{
                    try {
                        rpcListener.callBack(rpcEvent.getData());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    /**
     * 同步事件处理，可能会堵塞
     */
    public static void sendSyncEvent(RpcEvent iRpcEvent) {
        if (CommonUtil.isEmptyList(rpcListenerList)) {
            return;
        }
        for (RpcListener<?> rpcListener : rpcListenerList) {
            Class<?> type = getInterfaceT(rpcListener);
            if (type != null && type.equals(iRpcEvent.getClass())) {
                try {
                    rpcListener.callBack(iRpcEvent.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取接口上的泛型T
     */
    public static Class<?> getInterfaceT(Object o) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        Type type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        return null;
    }

}
