package cn.zurish.snow.core.serialize.fastjson;

import cn.zurish.snow.core.serialize.SerializeFactory;
import com.alibaba.fastjson2.JSON;

/**
 * 2023/12/31 14:37
 */
public class FastJsonSerializeFactory implements SerializeFactory {

    @Override
    public <T> byte[] serialize(T t) {
        String jsonStr = JSON.toJSONString(t);
        return jsonStr.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data),clazz);
    }

}
