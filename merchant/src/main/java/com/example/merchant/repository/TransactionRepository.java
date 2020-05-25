package com.example.merchant.repository;

import com.example.merchant.model.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

    @Modifying
    @Query("delete from Transaction t where t.created < :before")
    int deleteAllOlderThan(@Param("before") Instant before);

}
