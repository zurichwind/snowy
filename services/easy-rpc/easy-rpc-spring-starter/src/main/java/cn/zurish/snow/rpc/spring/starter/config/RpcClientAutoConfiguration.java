package cn.zurish.snow.rpc.spring.starter.config;

import cn.zurish.snow.core.client.Client;
import cn.zurish.snow.core.client.RpcReference;
import cn.zurish.snow.core.client.RpcReferenceWrapper;
import cn.zurish.snow.rpc.spring.starter.common.EasyRpcReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Field;

import static cn.zurish.snow.core.common.cache.CommonClientCache.CLIENT_CONFIG;

/**
 * 客户端自动配置类
 * 2023/12/31 19:22
 */
public class RpcClientAutoConfiguration implements BeanPostProcessor, ApplicationListener<ApplicationReadyEvent> {

    private static RpcReference rpcReference = null;
    private static Client client = null;
    private volatile boolean needInitClient = false;
    private volatile boolean hasInitClientConfig = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientAutoConfiguration.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EasyRpcReference.class)) {
                if (!hasInitClientConfig) {
                    client = new Client();
                    try {
                        client.initClientConfig();
                        rpcReference = client.initClientApplication();
                    } catch (Exception e) {
                        LOGGER.error("[IRpcClientAutoConfiguration] postProcessAfterInitialization has error ", e);
                        throw new RuntimeException(e);
                    }
                    hasInitClientConfig = true;
                }
                needInitClient = true;
                EasyRpcReference easyRpcReference = field.getAnnotation(EasyRpcReference.class);
                try {
                    field.setAccessible(true);
                    Object refObj = field.get(bean);
                    RpcReferenceWrapper rpcReferenceWrapper = new RpcReferenceWrapper();
                    rpcReferenceWrapper.setAimClass(field.getType());
                    rpcReferenceWrapper.setGroup(easyRpcReference.group());
                    rpcReferenceWrapper.setServiceToken(easyRpcReference.serviceToken());
                    rpcReferenceWrapper.setUrl(easyRpcReference.url());
                    rpcReferenceWrapper.setTimeOut(easyRpcReference.timeOut());
                    //失败重试次数
                    rpcReferenceWrapper.setRetry(easyRpcReference.retry());
                    rpcReferenceWrapper.setAsync(easyRpcReference.async());
                    refObj = rpcReference.get(rpcReferenceWrapper);
                    field.set(bean, refObj);
                    client.doSubscribeService(field.getType());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (needInitClient && client != null) {
            LOGGER.info(" ================== [{}] started success ================== ", CLIENT_CONFIG.getApplicationName());
            client.doConnectServer();
            client.startClient();
        }
    }
}