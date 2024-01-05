package cn.zurish.snow.core.rpc.hook;

import cn.zurish.snow.rpc.common.factory.ThreadPoolFactory;
import cn.zurish.snow.rpc.common.util.NacosUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 2024/1/1 23:14
 */
@Slf4j
public class ShutdownHook {

    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }


    public void addClearAllHook() {
        log.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));

    }

}
