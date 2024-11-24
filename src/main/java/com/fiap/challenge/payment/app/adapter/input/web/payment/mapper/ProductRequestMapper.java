package com.fiap.challenge.payment.app.adapter.input.web.payment.mapper;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.ProductRequest;
import com.fiap.challenge.payment.core.domain.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    Product toProduct(ProductRequest productRequest);

}
