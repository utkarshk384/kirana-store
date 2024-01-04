package com.app.kiranastore.repository;

import com.app.kiranastore.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByStoreId(Long id);
    Optional<Transaction> findBytransactionUID(String transactionId);

}
