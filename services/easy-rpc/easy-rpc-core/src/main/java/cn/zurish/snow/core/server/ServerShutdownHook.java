package cn.zurish.snow.core.server;

import cn.zurish.snow.core.common.event.RpcDestoryEvent;
import cn.zurish.snow.core.common.event.RpcListenerLoader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2023/12/30 13:40
 */
@Slf4j
public class ServerShutdownHook {


    /**
     * 注册一个shutdownHook的钩子，当jvm进程关闭的时候触发
     */
    public static void registryShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                RpcListenerLoader.sendSyncEvent(new RpcDestoryEvent("destroy"));
                log.info("server destruction");
            }
        }));
    }

}
