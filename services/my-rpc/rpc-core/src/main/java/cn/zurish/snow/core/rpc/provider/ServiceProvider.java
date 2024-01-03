package cn.zurish.snow.core.rpc.provider;

/**
 * 2024/1/1 23:16
 */
public interface ServiceProvider {

    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);

}
