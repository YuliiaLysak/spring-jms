package edu.lysak.springjms.service;

import edu.lysak.springjms.domain.BookOrder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookOrderService {

    private static final String BOOK_QUEUE = "book.order.queue";

    private final JmsTemplate jmsTemplate;

    public BookOrderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void send(BookOrder bookOrder, String storeId, String orderState) {
        jmsTemplate.convertAndSend(BOOK_QUEUE, bookOrder, message -> {
            message.setStringProperty("bookOrderId", bookOrder.getBookOrderId());
            message.setStringProperty("storeId", storeId);
            message.setStringProperty("orderState", orderState);
            return message;
        });
    }
}
