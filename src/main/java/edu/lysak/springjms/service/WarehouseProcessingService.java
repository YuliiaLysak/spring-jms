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
    public void processOrder(BookOrder bookOrder, String orderState, String storeId) {
        ProcessedBookOrder order = new ProcessedBookOrder(bookOrder, new Date(), new Date());

        if ("NEW".equalsIgnoreCase(orderState)) {
            add(bookOrder, storeId);
        } else if ("UPDATE".equalsIgnoreCase(orderState)) {
            update(bookOrder, storeId);
        } else if ("DELETE".equalsIgnoreCase(orderState)) {
            delete(bookOrder, storeId);
        }

        jmsTemplate.convertAndSend("book.order.processed.queue", order);
    }

    private void add(BookOrder bookOrder, String storeId) {
        log.info("ADDING A NEW ORDER TO THE DB");
    }

    private void update(BookOrder bookOrder, String storeId) {
        log.info("UPDATING AN ORDER TO THE DB");
    }

    private void delete(BookOrder bookOrder, String storeId) {
        log.info("DELETING THE ORDER FROM THE DB");
    }
}
