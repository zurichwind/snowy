package cn.zurish.snow.core.common.event;

/**
 * 节点更新事件
 * 2023/12/29 14:03
 */
public class RpcUpdateEvent implements RpcEvent{
    private Object data;

    public RpcUpdateEvent(Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public RpcEvent setData(Object data) {
        this.data = data;
        return this;
    }
}
