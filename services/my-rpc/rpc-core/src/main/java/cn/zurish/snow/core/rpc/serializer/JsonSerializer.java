package cn.zurish.snow.core.rpc.serializer;

/**
 * 使用JSON格式的序列化器
 * 2024/1/1 23:24
 */
public class JsonSerializer implements CommonSerializer{
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        return null;
    }

    @Override
    public int getCode() {
        return 0;
    }
}
