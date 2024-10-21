package com.seti.btg.domain.model.dto;

import com.seti.btg.domain.model.enumerator.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private BigDecimal balance;
    private NotificationType notificationType;
}