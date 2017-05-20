package com.soho.configuration.jms;

import com.soho.configuration.jms.properties.ActiveMqProperties;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * Created by ZhongChongtao on 2017/3/25.
 */
@Configuration
@EnableConfigurationProperties(ActiveMqProperties.class)
public class ActiveMqConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "soho.jms.activemq.queueNames", name = "postcard_cropQueue")
    @ConditionalOnClass(Queue.class)
    @ConditionalOnBean(ActiveMqProperties.class)
    public Queue queue(ActiveMqProperties activeMqProperties) {
        return new ActiveMQQueue(activeMqProperties.getQueueNames().get("postcard_cropQueue"));
    }
}
