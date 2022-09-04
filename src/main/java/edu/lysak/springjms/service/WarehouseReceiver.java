package edu.lysak.springjms.service;

import edu.lysak.springjms.domain.BookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WarehouseReceiver {

    @JmsListener(destination = "book.order.queue")
    public void receive(BookOrder bookOrder) {
        log.info("Message received!");
        log.info("Message is == " + bookOrder);
    }
}
