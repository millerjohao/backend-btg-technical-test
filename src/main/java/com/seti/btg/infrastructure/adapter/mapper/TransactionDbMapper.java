package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Transaction;
import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionDbMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "fund", target = "fund")
    @Mapping(source = "transactionType", target = "transactionType")
    @Mapping(source = "transactionDate", target = "transactionDate")
    @Mapping(source = "amount", target = "amount")
    TransactionEntity toDbo(Transaction domain);

    @InheritInverseConfiguration
    Transaction toDomain(TransactionEntity entity);
}
