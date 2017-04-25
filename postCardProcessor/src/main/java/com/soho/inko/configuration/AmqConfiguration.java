package com.soho.inko.configuration;

import com.soho.inko.configuration.properties.AmqProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;

@Configuration
@EnableConfigurationProperties(AmqProperties.class)
public class AmqConfiguration {

    @Bean
    public ActiveMQConnectionFactory connectionFactory(AmqProperties amqProperties) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL(amqProperties.getBrokerUrl());
        connectionFactory.setDispatchAsync(true);
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setProducerWindowSize(1024000);
        return connectionFactory;
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory) {
        return new JmsMessagingTemplate(connectionFactory);
    }
}
