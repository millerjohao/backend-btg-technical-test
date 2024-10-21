package com.seti.btg.infrastructure.rest.controller;

import com.seti.btg.application.service.FundService;
import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.request.FundRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para Fondos
 */
@RestController
@RequestMapping("/api/funds")
public class FundController {

    @Autowired
    private FundService fundService;

    /**
     * Endpoint que permite obtener todos los fondos
     *
     * @return
     */
    @GetMapping
    public List<FundDto> getAllFunds() {
        return fundService.getAll();
    }

    /**
     * Endpoint que permite crear un fondo
     *
     * @param fund
     * @return Objeto fondo
     */
    @PostMapping
    public FundDto createFund(@RequestBody FundRequest fund) {
        return fundService.createNewFund(fund);
    }

    /**
     * Endpoint que permite buscar un fondo por id
     *
     * @param id
     * @return Objeto fondo
     */
    @GetMapping("/{id}")
    public FundDto getFundById(@PathVariable long id) {
        return fundService.getFundById(id);
    }

    @GetMapping("/byUser/{id}")
    public List<FundSubscription> getFundsByCustomerId(@PathVariable long id) {
        return fundService.getFundsByCustomerId(id);
    }
}
