package com.seti.btg.domain.model.dto;

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
public class TransactionDto {
    private UUID id;
    private CustomerDto customer;
    private FundDto fund;
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private BigDecimal amount;
}
