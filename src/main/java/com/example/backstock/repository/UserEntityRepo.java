package com.example.backstock.repository;

import com.example.backstock.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity, Long> {

    Boolean existsByUserNameOrEmailAddress(String userName, String emailAddress);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmailAddress(String emailAddress);
}
