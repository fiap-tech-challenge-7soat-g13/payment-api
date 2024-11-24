package com.fiap.challenge.payment.app.adapter.input.web.payment.mapper;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.CustomerRequest;
import com.fiap.challenge.payment.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    Customer toCustomer(CustomerRequest customerRequest);

}
