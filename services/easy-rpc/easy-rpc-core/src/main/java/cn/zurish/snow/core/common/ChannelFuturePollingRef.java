package cn.zurish.snow.core.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 2023/12/29 13:26
 */
public class ChannelFuturePollingRef {
    private final AtomicLong referenceTimes = new AtomicLong(0);

    public ChannelFutureWrapper getChannelFutureWrapper(ChannelFutureWrapper[] wraps) {
        long i = referenceTimes.getAndIncrement();
        int index = (int) (i % wraps.length);
        return wraps[index];
    }
}
