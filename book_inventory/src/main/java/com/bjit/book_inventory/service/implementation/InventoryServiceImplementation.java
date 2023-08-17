package com.bjit.book_inventory.service.implementation;

import com.bjit.book_inventory.entity.BookInventoryEntity;
import com.bjit.book_inventory.exception.NotFoundException;
import com.bjit.book_inventory.exception.UnsuccessfulException;
import com.bjit.book_inventory.mapper.BookMapper;
import com.bjit.book_inventory.model.BookInventoryModel;
import com.bjit.book_inventory.repository.InventoryRepository;
import com.bjit.book_inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InventoryServiceImplementation implements InventoryService {

    public final InventoryRepository inventoryRepository;

    @Override
    public ResponseEntity<Object> update(BookInventoryModel updatedModel) {
        Optional<BookInventoryEntity> bookInventoryEntity = inventoryRepository.findById(updatedModel.getBookId());
        if (bookInventoryEntity.isPresent()) {
            BookInventoryEntity existingBook = bookInventoryEntity.get();
            existingBook.setBookId(updatedModel.getBookId());
            existingBook.setPrice(updatedModel.getPrice());
            existingBook.setQuantity(updatedModel.getQuantity());
            BookInventoryEntity saveEntity = inventoryRepository.save(existingBook);
            BookInventoryModel bookMapper = BookMapper.mapToBookModel(saveEntity);
            return new ResponseEntity<>(bookMapper, HttpStatus.OK);
        } else {
            throw new UnsuccessfulException("Inventory update unsuccessful");
        }
    }

    @Override
    public ResponseEntity<List<BookInventoryModel>> getBookDetailsList(List<Long> bookIds) {
        List<BookInventoryModel> inventoryModels = new ArrayList<>();

        for (Long bookId : bookIds) {
            BookInventoryModel model = retrieveBookInventoryModel(bookId);
            inventoryModels.add(model);
        }

        return ResponseEntity.ok(inventoryModels);
    }

    private BookInventoryModel retrieveBookInventoryModel(Long bookId) {
        Optional<BookInventoryEntity> bookInventoryEntity = inventoryRepository.findById(bookId);

        if (bookInventoryEntity.isPresent()) {
            BookInventoryEntity book = bookInventoryEntity.get();

            BookInventoryModel model = new BookInventoryModel();
            model.setBookId(book.getBookId());
            model.setPrice(book.getPrice());
            model.setQuantity(book.getQuantity());

            return model;
        } else {
            throw new UnsuccessfulException("Book not found");
        }
    }

    @Override
    public ResponseEntity<Object> deleteBookDetails(BookInventoryModel requestModel) {
        Long bookId = requestModel.getBookId();

        Optional<BookInventoryEntity> inventoryOptional = inventoryRepository.findByBookId(bookId);

        if (inventoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book inventory not found");
        }

        BookInventoryEntity inventory = inventoryOptional.get();

        inventoryRepository.delete(inventory);

        Map<String, Object> response = new HashMap<>();
        response.put("book", inventory);
        response.put("message", "Book inventory deleted successfully");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> getDetailById(Long bookId) {
        Optional<BookInventoryEntity> inventoryEntity = inventoryRepository.getBookByBookId(bookId);
        if (inventoryEntity.isPresent()) {
            BookInventoryEntity existingDetail = inventoryEntity.get();
            return new ResponseEntity<>(existingDetail, HttpStatus.OK);
        } else {
            throw new NotFoundException("Book not found");
        }
    }

    @Override
    public ResponseEntity<Object> create(BookInventoryModel bookInventoryModel) {
//        Optional<BookInventoryEntity> bookInventoryEntity = inventoryRepository.findByBookId(bookInventoryModel.getBookId());
//        if (bookInventoryEntity.isPresent()) {
//            throw new NotFoundException("Inventory Could not created");
//        } else {
//            BookInventoryEntity inventoryEntity = BookInventoryEntity.builder()
//                    .bookId(bookInventoryModel.getBookId())
//                    .price(bookInventoryModel.getPrice())
//                    .quantity(bookInventoryModel.getQuantity())
//                    .build();
//            BookInventoryEntity saveEntity = inventoryRepository.save(inventoryEntity);
//            BookInventoryModel bookMapper = BookMapper.mapToBookModel(saveEntity);
//            return new ResponseEntity<>(bookMapper, HttpStatus.OK);
//        }
        BookInventoryEntity inventoryEntity = BookInventoryEntity.builder()
                .bookId(bookInventoryModel.getBookId())
                .price(bookInventoryModel.getPrice())
                .quantity(bookInventoryModel.getQuantity())
                .build();
        BookInventoryEntity saveEntity = inventoryRepository.save(inventoryEntity);
        BookInventoryModel bookMapper = BookMapper.mapToBookModel(saveEntity);
        return new ResponseEntity<>(bookMapper, HttpStatus.OK);
    }
}
