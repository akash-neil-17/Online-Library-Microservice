package com.bjit.book_service.service;

import com.bjit.book_service.model.BookModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<List<BookModel>> bookList();
    ResponseEntity<Object> create(BookModel bookModel);
    ResponseEntity<Object> update( BookModel updatedBookModel);
    ResponseEntity<Object> delete(BookModel bookModel);
    ResponseEntity<Object> getBookByID(Long bookID);
    ResponseEntity<Object> buyBook(BookModel requestModel);
}
