package cn.zurish.snow.rpc.common.factory;

import cn.zurish.snow.rpc.common.exception.SnowException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 单例工厂
 * 2024/1/1 18:34
 */
public class SingletonFactory {

    private static final Map<Class<?>, Object> objectMap = new HashMap<>();

    private SingletonFactory() {}

    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        synchronized (clazz) {
            if (instance == null) {
                try {
                    instance = clazz.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    throw new SnowException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }

}