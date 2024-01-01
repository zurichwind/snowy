package cn.zurish.snow.core.common;

import io.netty.channel.ChannelFuture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义包装类 将netty建立好的ChannelFuture做了一些封装
 * 2023/12/29 13:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelFutureWrapper {


    private String host;

    private Integer port;

    private Integer weight;

    private String group;

    private ChannelFuture channelFuture;

}
