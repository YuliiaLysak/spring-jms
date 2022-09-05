package edu.lysak.springjms.service;

import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.ProcessedBookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WarehouseReceiver {

    private final WarehouseProcessingService warehouseProcessingService;

    public WarehouseReceiver(WarehouseProcessingService warehouseProcessingService) {
        this.warehouseProcessingService = warehouseProcessingService;
    }

    @JmsListener(destination = "book.order.queue")
    @SendTo("book.order.processed.queue")
    public ProcessedBookOrder receive(
            @Payload BookOrder bookOrder,
            @Header(name = "orderState") String orderState,
            @Header(name = "bookOrderId") String bookOrderId,
            @Header(name = "storeId") String storeId,
            MessageHeaders messageHeaders
    ) {
        log.info("Message received!");
        log.info("Message is == " + bookOrder);
        log.info("Message property orderState = {}, bookOrderId = {}, storeId = {}", orderState, bookOrderId, storeId);
        log.info("messageHeaders = {}", messageHeaders);

        // generates error for testing errorHandler
        if (bookOrder.getBook().getTitle().startsWith("L")) {
            throw new IllegalArgumentException(
                    "OrderId = "
                    + bookOrder.getBookOrderId()
                    + "begins with 'L' and these books are not allowed"
            );
        }

        return warehouseProcessingService.processOrder(bookOrder, orderState, storeId);
    }
}
