package com.lmhy.fisco.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Forest
 */
@Component
@ConfigurationProperties(prefix = "config.bcos")
@Data
public class ConfigProp {
    private String host;
    private Integer port;
    private String nodeId;
    private String orgId;
}
