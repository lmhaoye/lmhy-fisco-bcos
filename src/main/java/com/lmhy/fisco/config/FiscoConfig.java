package com.lmhy.fisco.config;

import com.lmhy.fisco.ext.FISCO;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.bcos.channel.client.Service;
import org.bcos.channel.handler.ChannelConnections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class FiscoConfig {

    @Autowired
    private ConfigProp configProp;
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(50);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(500);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPoolTaskExecutor;
    }

    @Bean
    public Service channelService() {
        Service service = new Service();

//        String orgId = "Mo";
        String orgId = configProp.getOrgId();
        service.setOrgID(orgId);
        ConcurrentHashMap<String, ChannelConnections> keyID2connections = new ConcurrentHashMap<>();
        ChannelConnections channelConnections = new ChannelConnections();
//        String host = "172.16.1.190";
        String host = configProp.getHost();
//        Integer port = 30304;
        Integer port = configProp.getPort();
//        String nodeId = "4f14825078e4336a783ccc9c2341c0c2f2797b3f3c63a784c970fd8f2f896f09aaa6cd93d08de2aaa7dc5d1d26b25b5b0c4a4a85e088bc0ee611c06e2ec2bdd1";


        List<String> connList = new ArrayList<>();
//        StringBuilder sb = new StringBuilder("NodeA");
        StringBuilder sb = new StringBuilder(configProp.getNodeId());
        sb.append("@")
                .append(host)
                .append(":")
                .append(port);
        connList.add(sb.toString());

        channelConnections.setConnectionsStr(connList);
        keyID2connections.put(orgId, channelConnections);
        service.setAllChannelConnections(keyID2connections);
        return service;
    }

    @PostConstruct
    public void init() {
        FISCO.init(this.channelService());
    }
}
