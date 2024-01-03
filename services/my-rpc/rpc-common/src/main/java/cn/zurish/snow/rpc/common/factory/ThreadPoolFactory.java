package cn.zurish.snow.rpc.common.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 2024/1/1 18:35
 */
@Slf4j
public class ThreadPoolFactory {

    /**
     * 线程池参数
     */
    private static final int CORE_POOL_SIZE = 10;

    private static final int MAXIMUM_POOL_SIZE_SIZE = 100;

    private static final int KEEP_ALIVE_TIME = 1;

    private static final int  BLOCKING_QUEUE_CAPACITY = 100;

    private static final Map<String, ExecutorService> threadPoolsMap =  new ConcurrentHashMap<>();

    private ThreadPoolFactory(){

    }

    public static ExecutorService createDefaultThreadPool(String threadNamePrefix) {
        return createDefaultThreadPool(threadNamePrefix, false);
    }

    public static ExecutorService createDefaultThreadPool(String threadNamePrefix, Boolean daemon) {
            ExecutorService pool = threadPoolsMap.computeIfAbsent(threadNamePrefix, k ->createDefaultThreadPool(threadNamePrefix, daemon));
            if (pool.isShutdown() || pool.isTerminated()) {
                threadPoolsMap.remove((threadNamePrefix));
                pool = createThreadPool(threadNamePrefix, daemon);
                threadPoolsMap.put(threadNamePrefix, pool);
            }
            return pool;
    }

    public static void shutDownAll() {
        log.info("关闭所有线程池");
        threadPoolsMap.entrySet().parallelStream().forEach(entry -> {
            ExecutorService executorService = entry.getValue();
            executorService.shutdown();
            log.info("关闭线程池 [{}] [{}]", entry.getKey(), executorService.isTerminated());
            try{
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.info("关闭线程池失败！ ");
                executorService.shutdownNow();
            }
        });
    }

    private static ExecutorService createThreadPool(String threadNamePrefix, Boolean daemon) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix, daemon);
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES, workQueue, threadFactory);
    }



    /**
     * 创建 ThreadFactory 。如果threadNamePrefix不为空则使用自建ThreadFactory，否则使用defaultThreadFactory
     *
     * @param threadNamePrefix 作为创建的线程名字的前缀
     * @param daemon           指定是否为 Daemon Thread(守护线程)
     * @return ThreadFactory
     */
    private static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null ) {
            if (daemon != null ) {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();
            } else {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
            }
        }
        return Executors.defaultThreadFactory();
    }



}
