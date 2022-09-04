package edu.lysak.springjms.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Slf4j
@Component
public class BookOrderProcessingMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            log.info(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
