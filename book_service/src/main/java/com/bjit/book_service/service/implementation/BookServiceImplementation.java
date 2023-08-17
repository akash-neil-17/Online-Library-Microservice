package com.bjit.book_service.service.implementation;

import com.bjit.book_service.entity.BookEntity;
import com.bjit.book_service.exception.NotFoundException;
import com.bjit.book_service.exception.UnsuccessfulException;
import com.bjit.book_service.model.BookModel;
import com.bjit.book_service.model.BookResponseModel;
import com.bjit.book_service.model.BuyBookModel;
import com.bjit.book_service.model.InventoryModel;
import com.bjit.book_service.repository.BookRepository;
import com.bjit.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final InventoryModel inventoryModel;
    private final BookResponseModel bookResponseModel;
    private final BuyBookModel buyBookModel;
    private static String endPoint = "http://localhost:8082/book-inventory";

    @Override
    @Transactional
    public ResponseEntity<Object> create(BookModel bookModel) {
        BookEntity bookEntity = BookEntity.builder()
                .bookId(bookModel.getBookId())
                .bookName(bookModel.getBookName())
                .authorName(bookModel.getAuthorName())
                .genre(bookModel.getGenre())
                .build();
        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        inventoryModel.setBookId(savedBookEntity.getBookId());
        inventoryModel.setPrice(bookModel.getPrice());
        inventoryModel.setQuantity(bookModel.getQuantity());

        ResponseEntity<InventoryModel> responseEntity = restTemplate.postForEntity(endPoint + "/create", inventoryModel, InventoryModel.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            bookResponseModel.setBookName(savedBookEntity.getBookName());
            bookResponseModel.setAuthorName(savedBookEntity.getAuthorName());
            bookResponseModel.setGenre(savedBookEntity.getGenre());
            bookResponseModel.setPrice(Objects.requireNonNull(responseEntity.getBody()).getPrice());
            bookResponseModel.setQuantity(responseEntity.getBody().getQuantity());
            return new ResponseEntity<>(bookResponseModel, HttpStatus.CREATED);
        } else {
            throw new UnsuccessfulException("Book entry unsuccessful");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(BookModel updatedBookModel) {
        Optional<BookEntity> bookEntity = bookRepository.findById(updatedBookModel.getBookId());
        if (bookEntity.isPresent()) {
            BookEntity existingBook = bookEntity.get();
            existingBook.setBookName(updatedBookModel.getBookName());
            existingBook.setAuthorName(updatedBookModel.getAuthorName());
            existingBook.setGenre(updatedBookModel.getGenre());
            BookEntity savedUpdate = bookRepository.save(existingBook);

            inventoryModel.setBookId(savedUpdate.getBookId());
            inventoryModel.setPrice(updatedBookModel.getPrice());
            inventoryModel.setQuantity(updatedBookModel.getQuantity());

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<InventoryModel> requestEntity = new HttpEntity<>(inventoryModel, headers);
            ResponseEntity<InventoryModel> responseEntity = restTemplate.exchange(
                    endPoint + "/update/book-id",
                    HttpMethod.PUT,  // Or the appropriate HTTP method
                    requestEntity,
                    InventoryModel.class  // The expected response object type
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                bookResponseModel.setBookName(savedUpdate.getBookName());
                bookResponseModel.setAuthorName(savedUpdate.getAuthorName());
                bookResponseModel.setGenre(savedUpdate.getGenre());
                bookResponseModel.setPrice(Objects.requireNonNull(responseEntity.getBody()).getPrice());
                bookResponseModel.setQuantity(responseEntity.getBody().getQuantity());
                return new ResponseEntity<>(bookResponseModel, HttpStatus.OK);
            } else {
                throw new NotFoundException("Book not found");
            }
        } else {
            throw new UnsuccessfulException("Update unsuccessful");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(BookModel bookModel) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookModel.getBookId());
        if (bookEntity.isPresent()) {
            BookEntity existingBook = bookEntity.get();
            inventoryModel.setBookId(existingBook.getBookId());

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<InventoryModel> requestEntity = new HttpEntity<>(inventoryModel, headers);
            ResponseEntity<InventoryModel> responseEntity = restTemplate.exchange(
                    endPoint + "/delete/book-id",
                    HttpMethod.DELETE,  // Or the appropriate HTTP method
                    requestEntity,
                    InventoryModel.class  // The expected response object type
            );
            bookRepository.delete(existingBook);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> getBookByID(Long BookId) {
        Optional<BookEntity> bookEntity = bookRepository.findById(BookId);
        if (bookEntity.isPresent()) {
            BookEntity existingBook = bookEntity.get();
            inventoryModel.setBookId(existingBook.getBookId());

            ResponseEntity<InventoryModel> responseEntity = restTemplate.getForEntity(endPoint + "/book-id/" + BookId, InventoryModel.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                bookResponseModel.setBookName(existingBook.getBookName());
                bookResponseModel.setAuthorName(existingBook.getAuthorName());
                bookResponseModel.setGenre(existingBook.getGenre());
                bookResponseModel.setPrice(Objects.requireNonNull(responseEntity.getBody()).getPrice());
                bookResponseModel.setQuantity(responseEntity.getBody().getQuantity());
            }
            return new ResponseEntity<>(bookResponseModel, HttpStatus.OK);
        } else {
            throw new NotFoundException("Book not found");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> buyBook(BookModel requestModel) {
        Optional<BookEntity> bookEntity = bookRepository.findById(requestModel.getBookId());
        if (bookEntity.isPresent()) {
            BookEntity existingBook = bookEntity.get();
            buyBookModel.setBookId(existingBook.getBookId());
            buyBookModel.setQuantity(requestModel.getQuantity());

            ResponseEntity<InventoryModel> responseEntity = restTemplate.getForEntity(endPoint + "/book-id/" + requestModel.getBookId(), InventoryModel.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {

                Integer presentQuantity = responseEntity.getBody().getQuantity();
                if (presentQuantity < buyBookModel.getQuantity()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    inventoryModel.setQuantity(presentQuantity - buyBookModel.getQuantity());
                    inventoryModel.setBookId(responseEntity.getBody().getBookId());
                    inventoryModel.setPrice(responseEntity.getBody().getPrice());

                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<InventoryModel> requestEntity = new HttpEntity<>(inventoryModel, headers);

                    ResponseEntity<InventoryModel> updateResponseEntity = restTemplate.exchange(
                            endPoint + "/update/book-id",
                            HttpMethod.PUT,  // Or the appropriate HTTP method
                            requestEntity,
                            InventoryModel.class  // The expected response object type
                    );
                    if (updateResponseEntity.getStatusCode() != HttpStatus.OK) {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    } else {
                        return new ResponseEntity<>(HttpStatus.OK);
                    }

                }
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<BookModel>> bookList() {
        //Fetching all books from book-service
        List<BookEntity> bookEntityList = bookRepository.findAll();
        //Creating a list for storing the BookIds from the bookEntityList
        List<Long> BookIds = bookEntityList.stream().map(BookEntity::getBookId).collect(Collectors.toList());

        HttpEntity<List<Long>> requestEntity = new HttpEntity<>(BookIds);
        ResponseEntity<List<InventoryModel>> inventoryResponseEntity = restTemplate.exchange(
                endPoint,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        if (inventoryResponseEntity.getStatusCode() == HttpStatus.OK) {
            //Creating a list for storing the response
            List<InventoryModel> responseModel = inventoryResponseEntity.getBody();
            List<BookModel> finalList = new ArrayList<>();
            for (int i = 0; i < bookEntityList.size(); i++) {
                BookEntity bookEntity = bookEntityList.get(i);
                assert responseModel != null;
                InventoryModel model = responseModel.get(i);
                BookModel bookModel = new BookModel();

                bookModel.setBookId(bookEntity.getBookId());
                bookModel.setBookName(bookEntity.getBookName());
                bookModel.setAuthorName(bookEntity.getAuthorName());
                bookModel.setGenre(bookEntity.getGenre());
                bookModel.setPrice(model.getPrice());
                bookModel.setQuantity(model.getQuantity());

                finalList.add(bookModel);
            }
            return new ResponseEntity<>(finalList, HttpStatus.OK);
        } else {
            throw new RuntimeException("Failed to retrieve book inventory details");
        }
    }
}
