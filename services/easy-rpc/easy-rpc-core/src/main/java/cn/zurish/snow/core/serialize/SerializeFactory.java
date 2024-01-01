package cn.zurish.snow.core.serialize;

/**
 * 序列化的接口
 * 2023/12/29 14:23
 */
public interface SerializeFactory {


    /**
     * 序列化
     *
     * @param t
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T t);

    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);
}
