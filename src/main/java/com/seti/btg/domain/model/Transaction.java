package com.seti.btg.domain.model;

import com.seti.btg.domain.model.enumerator.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    private UUID id;
    private Customer customer;
    private Fund fund;
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private BigDecimal amount;
}
