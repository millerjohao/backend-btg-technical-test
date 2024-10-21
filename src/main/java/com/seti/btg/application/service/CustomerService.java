package com.seti.btg.application.service;

import com.seti.btg.application.mapper.CustomerDtoMapper;
import com.seti.btg.application.mapper.CustomerRequestMapper;
import com.seti.btg.application.port.CustomerPort;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.request.CustomerRequest;
import com.seti.btg.domain.repository.CustomerRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements CustomerPort {
    @Autowired
    private CustomerRepositoryPort repository;
    @Autowired
    private CustomerDtoMapper dtoMapper;
    @Autowired
    private CustomerRequestMapper requestMapper;

    @Override
    public CustomerDto createNewUser(CustomerRequest customer) {
        var newCustomer = requestMapper.toDomain(customer);
        var createdCustomer = repository.createNewCustomer(newCustomer);
        return dtoMapper.toDto(createdCustomer);
    }

    @Override
    public List<CustomerDto> getAll() {
        var customers = repository.getAll();
        return customers
                .stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getUserById(Long id) {
        var customer = repository.getCustomerById(id);
        return dtoMapper.toDto(customer);
    }
}
