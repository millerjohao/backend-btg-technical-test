package com.seti.btg.infrastructure.adapter.serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionId implements Serializable {

    @Column(name = "id_customer")
    private Long idCustomer;

    @Column(name = "id_fund")
    private Long idFund;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId id = (SubscriptionId) o;
        return Objects.equals(idCustomer, id.idCustomer) &&
                Objects.equals(idFund, id.idFund);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer, idFund);
    }
}
