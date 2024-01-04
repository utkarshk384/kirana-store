package com.app.kiranastore.controller;

import com.app.kiranastore.api.FXRate;
import com.app.kiranastore.dto.DailyReportDto;
import com.app.kiranastore.model.Store;
import com.app.kiranastore.service.StoreService;
import com.app.kiranastore.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private FXRate currencyConverter;

    /**
     * @apiNote Will create a store based on the currency that is set by the user
     *
     * @param store - Request body
     * @param currency - Enum ("INR" | "USD")
     *
     * @throws com.app.kiranastore.exception.InternalServerException
     * @throws com.app.kiranastore.exception.BadRequestExcepetion
     * @throws com.app.kiranastore.exception.NotFoundException
     *
     * @return ResponseEntity<Map<String, Object>>
     * */
    @PostMapping("/stores/create")
    ResponseEntity<Object> newStore(@RequestBody Store store, @RequestParam String currency){

        // Convert currency to USD based on input
        // Calling the API for each request to convert prices in realtime
        if(!currency.equals("INR")){
            store.setBalance(currencyConverter.INRtoUSD(store.getBalance()));
        }

        Store createdStore = storeService.addStore(store);

        return Response.generateResponse("Successfully added store.", HttpStatus.CREATED, createdStore);
    }


    /**
     * @apiNote Will return a list of stores or an empty list.
     **
     * @return ResponseEntity<Map<String, Object>>
     * */
    @GetMapping("/stores/list")
    ResponseEntity<Object> listStore(){
        List<Store> stores =  storeService.listAll();

        return Response.generateResponse("Successfully listed stores.", HttpStatus.OK, stores);
    }


    /**
     * @apiNote Will return the store or throw an exception
     *
     * @param id - The ID of the store.
     *
     * @throws com.app.kiranastore.exception.NotFoundException
     *
     * @return ResponseEntity<Map<String, Object>>
     * */
    @GetMapping("/stores/{id}")
    ResponseEntity<Object> getOne(@PathVariable Long id){

        Store store = storeService.getById(id);

        return Response.generateResponse("Successfully fetched Store.", HttpStatus.OK, store);
    }


    /**
     * @apiNote Fetches the daily transactions of a store
     *
     * @param id - The ID of the store.
     * @param currency - Enum ("INR" | "USD")
     *
     * @throws com.app.kiranastore.exception.InternalServerException
     * @throws com.app.kiranastore.exception.BadRequestExcepetion
     * @throws com.app.kiranastore.exception.NotFoundException
     *
     * @return ResponseEntity<Map<String, Object>>
     * */
    @GetMapping("/report/{id}")
    ResponseEntity<Object> getDailyTransactions(@PathVariable Long id, @RequestParam(defaultValue = "INR", required = false) String currency){
        // Return amount in desired currency
        BigDecimal conversionRate = BigDecimal.valueOf(1);

        // Expecting that price the converted price should be precise therefore, calling the API for each request to receive prices in realtime
        if(currency != "INR")
            conversionRate = currencyConverter.getCurrencyPrice(currency);

        DailyReportDto dailyReport = storeService.dailyReport(id, conversionRate);

        return Response.generateResponse("Successfully generated report", HttpStatus.OK, dailyReport);
    }
}
