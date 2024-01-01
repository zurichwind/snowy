package cn.zurish.snow.core.common.event;

/**
 * 抽象事件，用于装载需要传递的数据信息
 * 2023/12/29 13:38
 */
public interface RpcEvent {
    /**
     * 获取事件数据
     * @return
     */
    Object getData();

    /**
     * 设置数据
     * @param data
     * @return
     */
    RpcEvent setData(Object data);
}

