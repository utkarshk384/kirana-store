package com.app.kiranastore.service;

import com.app.kiranastore.dto.DailyReportDto;
import com.app.kiranastore.dto.ReportDto;
import com.app.kiranastore.exception.NotFoundException;
import com.app.kiranastore.mapper.ReportMapper;
import com.app.kiranastore.model.Store;
import com.app.kiranastore.model.Transaction;
import com.app.kiranastore.repository.StoreRepository;
import com.app.kiranastore.repository.TransactionRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReportMapper reportMapper;

    public Store addStore(Store store){
        return storeRepo.save(store);
    }

    public List<Store> listAll(){
        return storeRepo.findAll();
    }

    public Store getById(Long id){
        return storeRepo.findById(id).orElseThrow(() -> new NotFoundException("Couldn't find the store with the given id of " + id));
    }

    public DailyReportDto dailyReport(Long id, BigDecimal conversionRate){

        // Specification to get all records for specific day while also sorting it by latest transaction first
        Specification<Transaction> spec = new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String today = "%" + dtf.format(LocalDateTime.now()) + "%";

                cq.orderBy(cb.desc(root.get("createdAt")));
                predicates.add(cb.like(root.get("createdAt").as(String.class), today));
                predicates.add(cb.equal(root.get("store").get("id"), id));

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };

        Store store = storeRepo.findById(id).orElseThrow(() -> new NotFoundException("Couldn't find store!"));

        // balance[0] will be the starting balance and balance[1] is the closing balance
        final BigDecimal[] balance = {
                store.getBalance().multiply(conversionRate),
                store.getBalance().multiply(conversionRate)
        };

        // Get all records and map it to ReportDTO with appropriate conversions.
        List<ReportDto> items = transactionRepository.findAll(spec).stream()
                .map(item -> reportMapper.EntitytoReportDTO(item, balance, conversionRate))
                .collect(Collectors.toList());

        return reportMapper.toDailyReport(items, balance);
    }

}
