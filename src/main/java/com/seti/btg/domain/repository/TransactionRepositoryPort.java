package com.seti.btg.domain.repository;

import com.seti.btg.domain.model.Transaction;

import java.util.List;

public interface TransactionRepositoryPort {


    Transaction createNewTransaction(Transaction transaction);

    List<Transaction> getAll();

    List<Transaction> getTransactionsByUser(Long id);

    Transaction cancelTransaction(Long customerId, Long fundId);
}
