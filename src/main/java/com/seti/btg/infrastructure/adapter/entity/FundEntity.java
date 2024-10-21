package com.seti.btg.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "funds")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "min_amount")
    private BigDecimal minAmount;
    private String category;

    @OneToMany(mappedBy = "fund", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    @ManyToMany(mappedBy = "subscribedFunds")
    private Set<CustomerEntity> customers = new HashSet<>();
}
