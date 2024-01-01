package cn.zurish.snow.core.common.event;

import lombok.AllArgsConstructor;

/**
 * 2023/12/29 13:40
 */
@AllArgsConstructor
public class RpcDestoryEvent implements RpcEvent{

    private Object data;
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
