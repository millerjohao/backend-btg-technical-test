package com.seti.btg.application.port;

import com.seti.btg.domain.model.dto.CustomerDto;
import com.seti.btg.domain.model.request.CustomerRequest;

import java.util.List;

public interface CustomerPort {
    CustomerDto createNewUser(CustomerRequest customer);
    List<CustomerDto> getAll();
    CustomerDto getUserById(Long id);
}
