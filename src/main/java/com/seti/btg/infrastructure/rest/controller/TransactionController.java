package com.seti.btg.infrastructure.rest.controller;

import com.seti.btg.application.service.TransactionService;
import com.seti.btg.domain.model.dto.TransactionDto;
import com.seti.btg.domain.model.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador para Transacciones
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * Endpoint que permite obtener el historico de transacciones completo
     * @return Lista de transacciones
     */
    @GetMapping
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAll();
    }

    /**
     * Endpoint que permite subscribirse a un fondo
     * @param transaction contrato tipo trnsacción para la creación
     * @return Objeto transacción
     */
    @PostMapping("/subscribe-to-fund")
    public TransactionDto createSubscription(@RequestBody TransactionRequest transaction) {
        return transactionService.createNewTransaction(transaction);
    }

    /**
     * Endpoint que permite listar las transacciones de un cliente por su id
     * @param id del cliente
     * @return Listado de transacciones
     */
    @GetMapping("/{id}")
    public List<TransactionDto> getTransactionsByUser(@PathVariable long id) {
        return transactionService.getTransactionsByUser(id);
    }

    /**
     * Endpoint que permite cancelar la subscripción de un cliente en un fondo específico
     * @param customerId id del cliente
     * @param fundId id del fondo
     * @return Objeto transacción
     */
    @PostMapping("/cancel-subscription")
    public TransactionDto cancelTransaction(@RequestParam Long customerId, @RequestParam Long fundId) {
        return transactionService.cancelTransaction(customerId, fundId);
    }
}
