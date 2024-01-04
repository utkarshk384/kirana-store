package com.app.kiranastore.service;

import com.app.kiranastore.enums.TransactionType;
import com.app.kiranastore.exception.ConflictException;
import com.app.kiranastore.exception.NotFoundException;
import com.app.kiranastore.model.Store;
import com.app.kiranastore.model.Transaction;
import com.app.kiranastore.repository.StoreRepository;
import com.app.kiranastore.repository.TransactionRepository;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private StoreRepository storeRepo;

    @Transactional
    public Transaction makeTransaction(Transaction transaction){
        // Set the transaction ID using NanoID
        transaction.setTransactionUID(NanoIdUtils.randomNanoId());

        // Identify the transaction type and deduct accordingly
        Store store = storeRepo.findById(transaction.getStore().getId()).orElseThrow(() -> new NotFoundException("Store not found!"));

        BigDecimal finalBalance = this.updateBalance(transaction.getTransactionType(), store.getBalance(), transaction.getAmount());

        if(transaction.getTransactionType() == TransactionType.DEBIT && finalBalance.compareTo(BigDecimal.valueOf(0)) == -1)
            throw new ConflictException("Not enough money to deduct");
        store.setBalance(finalBalance);

        // Update store
        storeRepo.save(store);

        return transactionRepo.save(transaction);
    }

    public List<Transaction> getByStoreId(Long storeId){

        return transactionRepo.findByStoreId(storeId);
    }

    public Transaction getByTransactionId(String transactionId){
        return transactionRepo.findBytransactionUID(transactionId).orElseThrow(() -> new NotFoundException("Couldn't find a transaction with the given id of " + transactionId));
    }

    private BigDecimal updateBalance(TransactionType transactionType, BigDecimal currentBalance, BigDecimal amount){
        // Update balance based on transaction type
        if(transactionType == TransactionType.CREDIT)
            currentBalance = currentBalance.add(amount);
        else if (transactionType == TransactionType.DEBIT)
            currentBalance = currentBalance.subtract(amount);

        return currentBalance;
    }

}
