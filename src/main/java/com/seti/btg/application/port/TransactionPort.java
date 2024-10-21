package com.seti.btg.application.port;

import com.seti.btg.domain.model.dto.TransactionDto;
import com.seti.btg.domain.model.request.TransactionRequest;

import java.util.List;

public interface TransactionPort {
    TransactionDto createNewTransaction(TransactionRequest transaction);

    List<TransactionDto> getAll();

    List<TransactionDto> getTransactionsByUser(Long id);

    TransactionDto cancelTransaction(Long customerId, Long fundId);
}
