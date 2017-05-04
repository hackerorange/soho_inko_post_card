package com.soho.inko.configuration;

        import com.soho.inko.configuration.properties.ActiveMqProperties;
        import org.apache.activemq.command.ActiveMQQueue;
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
    @ConditionalOnProperty(prefix = "soho.jms.activemq.queueNames",  name = "postcard_cropQueue")
    public Queue queue(ActiveMqProperties activeMqProperties) {
        return new ActiveMQQueue(activeMqProperties.getQueueNames().get("postcard_cropQueue"));
    }
}
