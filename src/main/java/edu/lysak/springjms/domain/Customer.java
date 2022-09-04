package edu.lysak.springjms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {

    private final String customerId;
    private final String fullName;

    @JsonCreator
    public Customer(
            @JsonProperty("customerId") String customerId,
            @JsonProperty("fullName") String fullName
    ) {
        this.customerId = customerId;
        this.fullName = fullName;
    }
}
