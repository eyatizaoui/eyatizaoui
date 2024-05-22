package com.example.backstock.repository;

import com.example.backstock.entity.ConfirmationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<ConfirmationKey,Long> {
    boolean existsByEmailAddress(String emailAddress);

    boolean existsByValue(String value);

    ConfirmationKey findByValue(String value);

    @Query(value ="select email_address from confirmation_keys where value =?1" , nativeQuery = true)
    String getEmailAddress(String value);
}
