package com.seti.btg.infrastructure.rest.controller;

import com.seti.btg.application.service.CustomerService;
import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.request.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para Clientes
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Endpoint que permite obtener todos los clientes
     * @return Lista de clientes
     */
    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAll();
    }

    /**
     * Endpoint que permite crear un cliente
     * @param customer
     * @return Objeto cliente
     */
    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerRequest customer) {
        return customerService.createNewUser(customer);
    }

    /**
     * Endpoint que permite buscar usuario por id
     * @param id
     * @return Objeto cliente
     */
    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable long id) {
        return customerService.getUserById(id);
    }
}
