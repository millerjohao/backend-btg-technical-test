package com.seti.btg.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscription {
    private Long idCustomer;
    private Long idFund;
    private BigDecimal amount;
}
