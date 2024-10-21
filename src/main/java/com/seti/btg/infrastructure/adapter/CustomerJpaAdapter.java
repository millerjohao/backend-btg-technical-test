package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.Customer;
import com.seti.btg.domain.repository.CustomerRepositoryPort;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.CustomerDbMapper;
import com.seti.btg.infrastructure.adapter.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerJpaAdapter implements CustomerRepositoryPort {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerDbMapper dbMapper;

    /**
     * Crea un nuevo cliente
     * @param customer
     * @return
     */
    @Override
    public Customer createNewCustomer(Customer customer) {

        if (repository.existsByEmail(customer.getEmail()) || repository.existsByPhone(customer.getPhone())) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorConstant.CUSTOMER_ALREADY_EXISTS);
        }

        var newCustomer = dbMapper.toDbo(customer);
        var customerCreated = repository.save(newCustomer);
        return dbMapper.toDomain(customerCreated);
    }

    /**
     * Retorna una lista de clientes
     * @return
     */
    @Override
    public List<Customer> getAll() {
        return repository.findAll().stream().map(dbMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su id
     * @param id
     * @return
     */
    @Override
    public Customer getCustomerById(Long id) {
        var customer = repository.findById(id);

        if (customer.isEmpty()) {
            throw new ErrorException(HttpStatus.NOT_FOUND, String.format(ErrorConstant.CUSTOMER_NOT_FOUND, id));
        }

        return dbMapper.toDomain(customer.get());
    }
}
