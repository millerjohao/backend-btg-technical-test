package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "fund", target = "fund")
    @Mapping(source = "transactionType", target = "transactionType")
    @Mapping(source = "transactionDate", target = "transactionDate")
    @Mapping(source = "amount", target = "amount")
    TransactionDto toDto(Transaction domain);
}


