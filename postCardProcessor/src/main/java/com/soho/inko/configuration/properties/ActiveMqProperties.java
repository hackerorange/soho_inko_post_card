package com.soho.inko.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by ZhongChongtao on 2017/3/25.
 */
@Configuration
@ConfigurationProperties(prefix = "soho.jms.activemq")
public class ActiveMqProperties {
    private Map<String, String> queueNames;

    public Map<String, String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(Map<String, String> queueNames) {
        this.queueNames = queueNames;
    }
}
