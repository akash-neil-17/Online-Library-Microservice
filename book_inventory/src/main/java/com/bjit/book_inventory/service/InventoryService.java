package com.bjit.book_inventory.service;

import com.bjit.book_inventory.model.BookInventoryModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    ResponseEntity<Object> update(BookInventoryModel updatedModel);

    ResponseEntity<List<BookInventoryModel>> getBookDetailsList(List<Long> bookIds);

    ResponseEntity<Object> deleteBookDetails(BookInventoryModel requestModel);

    ResponseEntity<Object> getDetailById(Long bookId);

    ResponseEntity<Object> create(BookInventoryModel bookInventoryModel);
}
