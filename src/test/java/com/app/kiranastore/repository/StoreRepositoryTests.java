package com.app.kiranastore.repository;

import com.app.kiranastore.model.Store;
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
public class StoreRepositoryTests {

    @Autowired
    private StoreRepository storeRepo;

    @Test
    public void storeRepo_Save_ReturnStore(){
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);

        Assertions.assertThat(savedStore).isNotNull();
        Assertions.assertThat(savedStore.getId()).isGreaterThan(0);
        Assertions.assertThat(savedStore.getBalance()).isEqualTo(BigDecimal.valueOf(500));
    }

    @Test
    public void storeRepo_findOne_ReturnStore(){
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);
        Optional<Store> queriedStore = storeRepo.findById(savedStore.getId());

        Assertions.assertThat(queriedStore).isNotNull();
    }

    @Test
    public void storeRepo_findOne_ReturnNULL(){
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);
        Optional<Store> queriedStore = storeRepo.findById(-1L);

        Assertions.assertThat(queriedStore).isNotNull();
    }

    @Test
    public void storeRepo_delete_null(){
        Store store = Store.builder()
                .address("Bangalore")
                .phone("9995557773")
                .balance(BigDecimal.valueOf(500)) // in INR
                .build();

        Store savedStore = storeRepo.save(store);
        storeRepo.delete(savedStore);
        
        Optional<Store> queriedStore = storeRepo.findById(savedStore.getId());

        Assertions.assertThat(queriedStore).isEmpty();
    }
}
