package com.bjit.book_inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInventoryModel {
    @NotNull
    private Long bookId;
    @NotNull
    private Double price;
    @NotNull
    private Integer quantity;
}
