package edu.lysak.springjms.service;

import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.ProcessedBookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class WarehouseProcessingService {

    @Transactional
    public Message<ProcessedBookOrder> processOrder(BookOrder bookOrder, String orderState, String storeId) {
        if ("NEW".equalsIgnoreCase(orderState)) {
            return add(bookOrder, storeId);
        } else if ("UPDATE".equalsIgnoreCase(orderState)) {
            return update(bookOrder, storeId);
        } else if ("DELETE".equalsIgnoreCase(orderState)) {
            return delete(bookOrder, storeId);
        } else {
            throw new IllegalArgumentException("WarehouseProcessingService.processOrder(...) - " +
                    "orderState did not match expected values");
        }
    }

    private Message<ProcessedBookOrder> add(BookOrder bookOrder, String storeId) {
        log.info("ADDING A NEW ORDER TO THE DB");
        ProcessedBookOrder processedBookOrder = new ProcessedBookOrder(bookOrder, new Date(), new Date());
        return build(processedBookOrder, "ADDED", storeId);
    }

    private Message<ProcessedBookOrder> update(BookOrder bookOrder, String storeId) {
        log.info("UPDATING AN ORDER TO THE DB");
        ProcessedBookOrder processedBookOrder = new ProcessedBookOrder(bookOrder, new Date(), new Date());
        return build(processedBookOrder, "UPDATED", storeId);
    }

    private Message<ProcessedBookOrder> delete(BookOrder bookOrder, String storeId) {
        log.info("DELETING THE ORDER FROM THE DB");
        ProcessedBookOrder processedBookOrder = new ProcessedBookOrder(bookOrder, new Date(), null);
        return build(processedBookOrder, "DELETED", storeId);
    }

    private Message<ProcessedBookOrder> build(ProcessedBookOrder bookOrder, String orderState, String storeId){
        return MessageBuilder
                .withPayload(bookOrder)
                .setHeader("orderState", orderState)
                .setHeader("storeId", storeId)
                .build();
    }
}
