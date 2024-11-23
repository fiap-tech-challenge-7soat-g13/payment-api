package com.fiap.challenge.payment.app.adapter.input.web.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private Long id;
    private String name;
    private String email;
    private String document;

}
