package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.request.TransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionRequestMapper {
    @Mapping(source = "idCustomer", target = "customer")
    @Mapping(source = "idFund", target = "fund")
    @Mapping(source = "amount", target = "amount")
    Transaction toDomain(TransactionRequest request);
    default Customer mapCustomer(Long idCustomer) {
        if (idCustomer == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(idCustomer);
        return customer;
    }
    default Fund mapFund(Long idFund) {
        if (idFund == null) {
            return null;
        }
        Fund fund = new Fund();
        fund.setId(idFund);
        return fund;
    }
}
