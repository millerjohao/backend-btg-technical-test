package com.seti.btg.domain.model.request;

import com.seti.btg.domain.model.enumerator.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRequest {
    private String name;
    private String email;
    private String phone;
    private NotificationType notificationType;
}
