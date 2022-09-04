package edu.lysak.springjms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Book {

    private final String bookId;
    private final String title;

    @JsonCreator
    public Book(
            @JsonProperty("bookId") String bookId,
            @JsonProperty("title")String title
    ) {
        this.bookId = bookId;
        this.title = title;
    }
}
