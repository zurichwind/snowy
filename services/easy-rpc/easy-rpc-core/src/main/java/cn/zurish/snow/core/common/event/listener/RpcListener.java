package cn.zurish.snow.core.common.event.listener;

/**
 * 监听器接口
 * 2023/12/29 13:47
 */
public interface RpcListener<T> {

    /**
     * 事件回调方法
     *
     * @param o
     */
    void callBack(Object o);
}
