package com.app.kiranastore.controller;

import com.app.kiranastore.api.FXRate;
import com.app.kiranastore.dto.TransactionDto;
import com.app.kiranastore.mapper.TransactionMapper;
import com.app.kiranastore.model.Transaction;
import com.app.kiranastore.service.TransactionService;
import com.app.kiranastore.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FXRate currencyConverter;

    @Autowired
    private TransactionMapper mapper;

    @GetMapping("/transactions/store/{storeId}")
    public ResponseEntity<Object> getAllTransaction(@PathVariable Long storeId){

        List<Transaction> transactions = transactionService.getByStoreId(storeId);

        return Response.generateResponse("Successfully returned all transactions with storeId " + storeId, HttpStatus.OK, transactions);
    }

    @PostMapping("/transactions/create")
    ResponseEntity<Object> addTransaction(@RequestBody TransactionDto body, @RequestParam String currency) {
        Transaction transaction = mapper.DTOtoEntity(body);

        // Convert currency from USD to INR
        if(!currency.equals("INR"))
            transaction.setAmount(currencyConverter.INRtoUSD(transaction.getAmount()));

        TransactionDto createdTransactionDto = mapper.EntitytoDTO(transactionService.makeTransaction(transaction));

        return Response.generateResponse("Successfully created transaction", HttpStatus.CREATED, createdTransactionDto);
    }

}
