package com.app.kiranastore.mapper;

import com.app.kiranastore.dto.TransactionDto;
import com.app.kiranastore.model.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {
    @Autowired
    private ModelMapper mm;

    public Transaction DTOtoEntity(TransactionDto dto){
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        Transaction transaction = new Transaction();

        transaction = mm.map(dto, Transaction.class);

        return transaction;
    }

    public TransactionDto EntitytoDTO(Transaction transaction){
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        TransactionDto dto = new TransactionDto();

        dto = mm.map(transaction, TransactionDto.class);

        return dto;
    }
}
