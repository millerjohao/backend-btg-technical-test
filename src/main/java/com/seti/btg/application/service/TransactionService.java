package com.seti.btg.application.service;

import com.seti.btg.application.mapper.TransactionDtoMapper;
import com.seti.btg.application.mapper.TransactionRequestMapper;
import com.seti.btg.application.port.TransactionPort;
import com.seti.btg.domain.model.Transaction;
import com.seti.btg.domain.model.dto.TransactionDto;
import com.seti.btg.domain.model.request.TransactionRequest;
import com.seti.btg.domain.repository.CustomerRepositoryPort;
import com.seti.btg.domain.repository.TransactionRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements TransactionPort {
    @Autowired
    private TransactionRepositoryPort transactionRepositoryPort;
    @Autowired
    private TransactionDtoMapper transactionDtoMapper;
    @Autowired
    private TransactionRequestMapper requestMapper;

    @Autowired
    private CustomerRepositoryPort customerRepositoryPort;

    @Override
    public TransactionDto createNewTransaction(TransactionRequest transaction) {
        var newTransaction = requestMapper.toDomain(transaction);
        var createdTransaction = transactionRepositoryPort.createNewTransaction(newTransaction);
        return transactionDtoMapper.toDto(createdTransaction);
    }

    @Override
    public List<TransactionDto> getAll() {
        var transactions = transactionRepositoryPort.getAll();
        return transactions.stream().map(transactionDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionsByUser(Long id) {
        var transactions = transactionRepositoryPort.getTransactionsByUser(id);
        return transactions.stream().map(transactionDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDto cancelTransaction(Long customerId, Long fundId) {
        Transaction transaction = transactionRepositoryPort.cancelTransaction(customerId, fundId);
        return transactionDtoMapper.toDto(transaction);
    }
}
