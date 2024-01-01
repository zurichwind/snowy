package cn.zurish.snow.core.common.event;

/**
 * 2023/12/29 14:05
 */
public class RpcNodeUpdateEvent implements RpcEvent {

    private Object data;

    public RpcNodeUpdateEvent(Object data) {
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