package com.bjit.authentication_service.repository;

//import com.bjit.book_service.entity.UserEntity;
import com.bjit.authentication_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    public UserEntity findByEmail(String email);
}