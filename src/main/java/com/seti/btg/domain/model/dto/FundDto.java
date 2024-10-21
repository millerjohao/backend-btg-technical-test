package com.seti.btg.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FundDto {
    private Long id;
    private String name;
    private Double minAmount;
    private String category;
}