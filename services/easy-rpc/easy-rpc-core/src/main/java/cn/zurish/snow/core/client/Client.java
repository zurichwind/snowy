package cn.zurish.snow.core.client;

import cn.zurish.snow.core.common.RpcDecoder;
import cn.zurish.snow.core.common.RpcEncoder;
import cn.zurish.snow.core.common.RpcInvocation;
import cn.zurish.snow.core.common.RpcProtocol;
import cn.zurish.snow.core.common.config.PropertiesBootstrap;
import cn.zurish.snow.core.common.event.RpcListenerLoader;
import cn.zurish.snow.core.common.utils.CommonUtil;
import cn.zurish.snow.core.filter.ClientFilter;
import cn.zurish.snow.core.filter.client.ClientFilterChain;
import cn.zurish.snow.core.proxy.ProxyFactory;
import cn.zurish.snow.core.registry.AbstractRegister;
import cn.zurish.snow.core.registry.RegistryService;
import cn.zurish.snow.core.registry.URL;
import cn.zurish.snow.core.router.Router;
import cn.zurish.snow.core.serialize.SerializeFactory;
import com.alibaba.fastjson2.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.zurish.snow.core.common.cache.CommonClientCache.*;
import static cn.zurish.snow.core.common.constants.RpcConstants.DEFAULT_DECODE_CHAR;
import static cn.zurish.snow.core.spi.ExtensionLoader.EXTENSION_LOADER_CLASS_CACHE;

/**
 * 2023/12/29 15:22
 */
@Getter
public class Client {

    private AbstractRegister abstractRegister;

    @Getter
    private final Bootstrap bootstrap = new Bootstrap();

    public RpcReference initClientApplication() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //初始化管道，包含了编解码器和客户端响应类
                ByteBuf delimiter = Unpooled.copiedBuffer(DEFAULT_DECODE_CHAR.getBytes());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(CLIENT_CONFIG.getMaxServerRespDataSize(), delimiter));
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });

        //初始化连接器
        ConnectionHandler.setBootstrap(bootstrap);

        //初始化监听器
        RpcListenerLoader rpcListenerLoader = new RpcListenerLoader();
        rpcListenerLoader.init();

        //初始化路由策略
        String routerStrategy = CLIENT_CONFIG.getRouterStrategy();
        EXTENSION_LOADER.loadExtension(Router.class);
        LinkedHashMap<String, Class<?>> routerMap = EXTENSION_LOADER_CLASS_CACHE.get(Router.class.getName());
        Class<?> routerClass = routerMap.get(routerStrategy);
        if (routerClass == null) {
            throw new RuntimeException("no match routerStrategyClass for " + routerStrategy);
        }
        ROUTER = (Router) routerClass.getDeclaredConstructor().newInstance();

        //初始化序列化器
        String clientSerialize = CLIENT_CONFIG.getClientSerialize();
        EXTENSION_LOADER.loadExtension(SerializeFactory.class);
        LinkedHashMap<String, Class<?>> serializeMap = EXTENSION_LOADER_CLASS_CACHE.get(SerializeFactory.class.getName());
        Class<?> serializeClass = serializeMap.get(clientSerialize);
        if (serializeClass == null) {
            throw new RuntimeException("no match serializeClass for " + clientSerialize);
        }
        CLIENT_SERIALIZE_FACTORY = (SerializeFactory) serializeClass.getDeclaredConstructor().newInstance();

        //初始化过滤链
        ClientFilterChain clientFilterChain = new ClientFilterChain();
        EXTENSION_LOADER.loadExtension(ClientFilter.class);
        LinkedHashMap<String, Class<?>> filterChainMap = EXTENSION_LOADER_CLASS_CACHE.get(ClientFilter.class.getName());
        for (Map.Entry<String, Class<?>> filterChainEntry : filterChainMap.entrySet()) {
            String filterChainKey = filterChainEntry.getKey();
            Class<?> filterChainImpl = filterChainEntry.getValue();
            if (filterChainImpl == null) {
                throw new RuntimeException("no match filterChainImpl for " + filterChainKey);
            }
            clientFilterChain.addClientFilter((ClientFilter) filterChainImpl.getDeclaredConstructor().newInstance());
        }
        CLIENT_FILTER_CHAIN = clientFilterChain;

        //初始化代理工厂
        String proxyType = CLIENT_CONFIG.getProxyType();
        EXTENSION_LOADER.loadExtension(ProxyFactory.class);
        LinkedHashMap<String, Class<?>> proxyTypeMap = EXTENSION_LOADER_CLASS_CACHE.get(ProxyFactory.class.getName());
        Class<?> proxyTypeClass = proxyTypeMap.get(proxyType);
        if (proxyTypeClass == null) {
            throw new RuntimeException("no match proxyTypeClass for " + proxyType);
        }
        return new RpcReference((ProxyFactory) proxyTypeClass.getDeclaredConstructor().newInstance());
    }

    public void initClientConfig() {
        CLIENT_CONFIG = PropertiesBootstrap.loadClientConfigFromLocal();
    }

    /**
     * 启动服务之前需要预先订阅对应的dubbo服务
     */
    public void doSubscribeService(Class<?> serviceBean) {
        if (abstractRegister == null) {
            try {
                //初始化注册中心
                String registerType = CLIENT_CONFIG.getRegisterType();
                EXTENSION_LOADER.loadExtension(RegistryService.class);
                LinkedHashMap<String, Class<?>> registerMap = EXTENSION_LOADER_CLASS_CACHE.get(RegistryService.class.getName());
                Class<?> registerClass = registerMap.get(registerType);
                abstractRegister = (AbstractRegister) registerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("registryServiceType unKnow,error is ", e);
            }
        }
        URL url = new URL();
        url.setApplicationName(CLIENT_CONFIG.getApplicationName());
        url.setServiceName(serviceBean.getName());
        url.addParameter("host", CommonUtil.getIpAddress());
        Map<String, String> result = abstractRegister.getServiceWeightMap(serviceBean.getName());
        URL_MAP.put(serviceBean.getName(), result);
        abstractRegister.subscribe(url);
    }

    /**
     * 开始和各个provider建立连接
     */
    public void doConnectServer() {
        for (URL providerUrl : SUBSCRIBE_SERVICE_LIST) {
            List<String> providerIps = abstractRegister.getProviderIps(providerUrl.getServiceName());
            for (String providerIp : providerIps) {
                try {
                    ConnectionHandler.connect(providerUrl.getServiceName(), providerIp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            URL url = new URL();
            url.setServiceName(providerUrl.getServiceName());
            url.addParameter("providerIps", JSON.toJSONString(providerIps));
            //客户端在此新增一个订阅的功能
            abstractRegister.doAfterSubscribe(url);
        }
    }

    /**
     * 开启发送线程，专门从事将数据包发送给服务端
     */
    public void startClient() {
        Thread asyncSendJob = new Thread(new AsyncSendJob(), "ClientAsyncSendJobThread");
        asyncSendJob.start();
    }

    /**
     * 异步发送信息任务
     */
    static class AsyncSendJob implements Runnable {

        public AsyncSendJob() { }

        @Override
        public void run() {
            while (true) {
                try {
                    //阻塞模式
                    RpcInvocation data = SEND_QUEUE.take();
                    //进行序列化
                    byte[] serialize = CLIENT_SERIALIZE_FACTORY.serialize(data);
                    //将RpcInvocation封装到RpcProtocol对象中，然后发送给服务端
                    RpcProtocol rpcProtocol = new RpcProtocol(serialize);
                    //获取netty通道
                    ChannelFuture channelFuture = ConnectionHandler.getChannelFuture(data);
                    //netty的通道负责发送数据给服务端
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}