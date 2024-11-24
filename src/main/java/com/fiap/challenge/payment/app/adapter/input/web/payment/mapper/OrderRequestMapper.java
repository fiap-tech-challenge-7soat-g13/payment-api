package com.fiap.challenge.payment.app.adapter.input.web.payment.mapper;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.OrderRequest;
import com.fiap.challenge.payment.core.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerRequestMapper.class, OrderProductRequestMapper.class})
public interface OrderRequestMapper {

    Order toOrder(OrderRequest orderRequest);

}
