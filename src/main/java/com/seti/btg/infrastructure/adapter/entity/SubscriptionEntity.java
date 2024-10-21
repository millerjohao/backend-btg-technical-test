package com.seti.btg.infrastructure.adapter.entity;

import com.seti.btg.infrastructure.adapter.serializable.SubscriptionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_fund_subscriptions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionEntity {
    @EmbeddedId
    private SubscriptionId id;

    @ManyToOne
    @MapsId("idCustomer")
    @JoinColumn(name = "id_customer", nullable = false)
    private CustomerEntity customer;

    @ManyToOne
    @MapsId("idFund")
    @JoinColumn(name = "id_fund", nullable = false)
    private FundEntity fund;
}
