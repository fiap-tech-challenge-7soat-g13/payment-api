package com.fiap.challenge.payment.app.adapter.input.web.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerRequest {

    private UUID id;
    private String name;
    private String email;
    private String document;

}
