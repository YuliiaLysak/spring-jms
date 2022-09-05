package edu.lysak.springjms.service;

import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.ProcessedBookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class WarehouseProcessingService {

    private final JmsTemplate jmsTemplate;

    public WarehouseProcessingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void processOrder(BookOrder bookOrder){
        ProcessedBookOrder order = new ProcessedBookOrder(
                bookOrder,
                new Date(),
                new Date()

        );
        jmsTemplate.convertAndSend("book.order.processed.queue", order);
    }
}
