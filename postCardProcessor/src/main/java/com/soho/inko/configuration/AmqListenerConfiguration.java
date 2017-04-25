package com.soho.inko.configuration;

import com.soho.inko.configuration.properties.AmqProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.jms.MessageListener;
import javax.jms.Queue;

@Component
@Configuration
@EnableConfigurationProperties(AmqProperties.class)
public class AmqListenerConfiguration {

//    private final MessageListener messageListener;
//
//    @Autowired
//    public AmqListenerConfiguration(MessageListener messageListener) {
//        this.messageListener = messageListener;
//    }

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(MessageListener messageListener, ActiveMQConnectionFactory connectionFactory, Queue queue, AmqProperties amqProperties) {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        defaultMessageListenerContainer.setDestinationName("");
        defaultMessageListenerContainer.setDestination(queue);
        defaultMessageListenerContainer.setMessageListener(messageListener);
        defaultMessageListenerContainer.setMaxMessagesPerTask(amqProperties.getMaxMessagePerTask());
        defaultMessageListenerContainer.setConcurrentConsumers(amqProperties.getConcurrentConsumers());
        defaultMessageListenerContainer.setIdleTaskExecutionLimit(amqProperties.getIdleTaskExecutionLimit());
        defaultMessageListenerContainer.setReceiveTimeout(amqProperties.getReceiveTimeOut());
        defaultMessageListenerContainer.setCacheLevel(3);
        defaultMessageListenerContainer.setSessionTransacted(false);
        return defaultMessageListenerContainer;
    }

}