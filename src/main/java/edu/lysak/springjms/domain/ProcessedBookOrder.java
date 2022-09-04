package edu.lysak.springjms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class ProcessedBookOrder {

    private final BookOrder bookOrder;
    private final Date processingDateTime;
    private final Date expectedShippingDateTime;

    @JsonCreator
    public ProcessedBookOrder(
            @JsonProperty("bookOrder") BookOrder bookOrder,
            @JsonProperty("processingDateTime") Date processingDateTime,
            @JsonProperty("expectedShippingDateTime") Date expectedShippingDateTime) {
        this.bookOrder = bookOrder;
        this.processingDateTime = processingDateTime;
        this.expectedShippingDateTime = expectedShippingDateTime;
    }
}
