package edu.lysak.springjms.service;

import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.ProcessedBookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class WarehouseProcessingService {

    @Transactional
    public ProcessedBookOrder processOrder(BookOrder bookOrder, String orderState, String storeId) {
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

    private ProcessedBookOrder add(BookOrder bookOrder, String storeId) {
        log.info("ADDING A NEW ORDER TO THE DB");
        return new ProcessedBookOrder(bookOrder, new Date(), new Date());
    }

    private ProcessedBookOrder update(BookOrder bookOrder, String storeId) {
        log.info("UPDATING AN ORDER TO THE DB");
        return new ProcessedBookOrder(bookOrder, new Date(), new Date());
    }

    private ProcessedBookOrder delete(BookOrder bookOrder, String storeId) {
        log.info("DELETING THE ORDER FROM THE DB");
        return new ProcessedBookOrder(bookOrder, new Date(), null);
    }
}
