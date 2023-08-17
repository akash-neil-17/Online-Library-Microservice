package com.bjit.book_inventory.mapper;

import com.bjit.book_inventory.entity.BookInventoryEntity;
import com.bjit.book_inventory.model.BookInventoryModel;

public class BookMapper {
    public static BookInventoryModel mapToBookModel(BookInventoryEntity bookInventoryEntity){
        return new BookInventoryModel(bookInventoryEntity.getBookId(), bookInventoryEntity.getPrice(), bookInventoryEntity.getQuantity());
    }
}
