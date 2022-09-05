package edu.lysak.springjms.controller;

import edu.lysak.springjms.domain.Book;
import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.Customer;
import edu.lysak.springjms.service.BookOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class Controller {

    private final BookOrderService bookOrderService;

    public Controller(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    List<Book> books = Arrays.asList(
            new Book("jpw-1234", "Lord of the Flies"),
            new Book("uyh-2345", "Being and Nothingness"),
            new Book("iuhj-87654", "At Sea and Lost"));


    List<Customer> customers = Arrays.asList(
            new Customer("mr-1234", "Michael Rodgers"),
            new Customer("jp-2345", "Jeff Peek"),
            new Customer("sm-8765", "Steve McClarney")
    );

    @GetMapping("/process/store/{storeId}/order/{orderId}/{customerId}/{bookId}/{orderState}")
    public String processOrder(
            @PathVariable("storeId") String storeId, // random value
            @PathVariable("orderId") String orderId, // random value
            @PathVariable("customerId") String customerId,
            @PathVariable("bookId") String bookId,
            @PathVariable("orderState") String orderState //NEW, UPDATE, DELETE
    ) {
        try {
            bookOrderService.send(build(customerId, bookId, orderId), storeId, orderState);
        } catch (Exception exception) {
            return "Error occurred! " + exception.getLocalizedMessage();
        }
        return "Order sent to warehouse for bookId = " + bookId + " from customerId = " + customerId + " successfully processed!";
    }

    private BookOrder build(String customerId, String bookId, String orderId) {
        Book myBook = books.stream()
                .filter(bk -> bk.getBookId().equalsIgnoreCase(bookId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Book selected does not exist in inventory!"));

        Customer myCustomer = customers.stream()
                .filter(ct -> ct.getCustomerId().equalsIgnoreCase(customerId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Customer selected does not appear to be valid!"));

        return new BookOrder(orderId, myBook, myCustomer);
    }

}
