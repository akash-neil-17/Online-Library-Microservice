package com.bjit.book_inventory.repository;

import com.bjit.book_inventory.entity.BookInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<BookInventoryEntity, Long> {
    Optional<BookInventoryEntity> findByBookId(Long bookId);
    Optional<BookInventoryEntity> getBookByBookId(Long bookId);

}
