package com.bjit.book_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseModel {
    private String bookName;
    private String authorName;
    private String genre;
    private Double price;
    private Integer quantity;
}
