package cn.zurish.snow.core.registry;

/**
 * 注册中心抽象层
 * 2023/12/29 14:20
 */
public interface RegistryService {

    /**
     * 注册url
     *
     * @param url
     */
    void register(URL url);

    /**
     * 服务下线
     *
     * @param url
     */
    void unRegister(URL url);

    /**
     * 消费方订阅服务
     *
     * @param url
     */
    void subscribe(URL url);


    /**
     * 执行取消订阅内部的逻辑
     *
     * @param url
     */
    void doUnSubscribe(URL url);
}
