package com.app.kiranastore.model;

import com.app.kiranastore.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
@Getter @Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, unique = true)
    private String transactionUID;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="storeId", referencedColumnName="id", nullable = false)
    private Store store;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    public Store getStore(){
        return this.store;
    }

}
