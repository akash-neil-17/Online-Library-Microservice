package com.bjit.book_inventory.controllers;

import com.bjit.book_inventory.model.BookInventoryModel;
import com.bjit.book_inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BookInventoryModel bookInventoryModel) {
        return inventoryService.create(bookInventoryModel);
    }

    @PutMapping("/update/book-id")
    public ResponseEntity<Object> update(@RequestBody BookInventoryModel updatedModel) {
        return inventoryService.update(updatedModel);
    }

    @GetMapping("/book-id/{bookId}")
    public ResponseEntity<Object> getDetailById(@PathVariable(value = "bookId") Long bookId) {
        return inventoryService.getDetailById(bookId);
    }

    @GetMapping()
    public ResponseEntity<List<BookInventoryModel>> getBookDetailsList(@RequestBody List<Long> bookIds) {
        return inventoryService.getBookDetailsList(bookIds);
    }

    @DeleteMapping("/delete/book-id")
    public ResponseEntity<Object> deleteBookDetails(@RequestBody BookInventoryModel requestModel) {
        return inventoryService.deleteBookDetails(requestModel);
    }

}
