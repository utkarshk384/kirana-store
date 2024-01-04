package com.app.kiranastore.repository;

import com.app.kiranastore.enums.TransactionType;
import com.app.kiranastore.model.Store;
import com.app.kiranastore.model.Transaction;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private StoreRepository storeRepo;

    @Test
    public void transactionRepo_Save_ReturnTransaction(){
        // Arrange
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);

        Transaction transaction = Transaction.builder()
                .transactionUID(NanoIdUtils.randomNanoId())
                .amount(BigDecimal.valueOf(200))
                .transactionType(TransactionType.CREDIT)
                .store(savedStore)
                .build();

        Transaction savedTransaction = transactionRepo.save(transaction);

        Assertions.assertThat(savedTransaction).isNotNull();
        Assertions.assertThat(savedTransaction.getId()).isGreaterThan(0);
    }

    @Test
    public void transactionRepo_findOne_ReturnTransaction(){
        // Arrange
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);

        Transaction transaction = Transaction.builder()
                .transactionUID(NanoIdUtils.randomNanoId())
                .amount(BigDecimal.valueOf(200))
                .transactionType(TransactionType.CREDIT)
                .store(savedStore)
                .build();

        Transaction savedTransaction = transactionRepo.save(transaction);

        Optional<Transaction> queriedTransaction = transactionRepo.findById(savedTransaction.getId());

        Assertions.assertThat(queriedTransaction).isNotNull();
    }

    @Test
    public void transactionRepo_findOne_ReturnNULL(){
        // Arrange
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);

        Transaction transaction = Transaction.builder()
                .transactionUID(NanoIdUtils.randomNanoId())
                .amount(BigDecimal.valueOf(200))
                .transactionType(TransactionType.CREDIT)
                .store(savedStore)
                .build();

        Transaction savedTransaction = transactionRepo.save(transaction);

        Optional<Transaction> queriedTransaction = transactionRepo.findById(-123L);

        Assertions.assertThat(queriedTransaction).isEmpty();
    }
}
