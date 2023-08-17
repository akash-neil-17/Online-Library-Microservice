package com.bjit.book_service.controllers;

import com.bjit.book_service.model.BookModel;
import com.bjit.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-service")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BookModel bookModel) {
        return bookService.create(bookModel);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody BookModel updatedBookModel) {
        return bookService.update(updatedBookModel);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody BookModel updatedBookModel) {
        return bookService.delete(updatedBookModel);
    }

    @GetMapping("/book/all")
    public ResponseEntity<List<BookModel>> getAll() {
        return bookService.bookList();
    }

    @GetMapping("/book/{bookID}")
    public ResponseEntity<Object> getBook(@PathVariable("bookID") Long bookID) {
        return bookService.getBookByID(bookID);
    }

    @PutMapping("/book/buy")
    public ResponseEntity<Object> buyBook(@RequestBody BookModel bookModel) {
        return bookService.buyBook(bookModel);
    }

}
