package com.fiap.challenge.payment.app.adapter.input.web.payment.mapper;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.OrderProductRequest;
import com.fiap.challenge.payment.core.domain.OrderProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductRequestMapper.class})
public interface OrderProductRequestMapper {

    OrderProduct toOrderProduct(OrderProductRequest orderProduct);

}
