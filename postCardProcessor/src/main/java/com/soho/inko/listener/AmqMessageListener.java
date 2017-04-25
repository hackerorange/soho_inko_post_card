package com.soho.inko.listener;

import com.soho.inko.handler.MessageHandler;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class AmqMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(AmqMessageListener.class);
    private final MessageHandler handler;

    @Autowired
    public AmqMessageListener(MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String text = ((ActiveMQTextMessage) message).getText();
            handler.handler(text);
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
