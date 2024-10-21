package com.seti.btg.domain.repository;

import com.seti.btg.domain.model.Customer;

import java.util.List;

public interface CustomerRepositoryPort {

    Customer createNewCustomer(Customer customer);

    List<Customer> getAll();

    Customer getCustomerById(Long id);

}
