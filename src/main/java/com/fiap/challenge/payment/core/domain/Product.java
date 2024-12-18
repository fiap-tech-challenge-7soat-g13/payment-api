package com.fiap.challenge.payment.core.domain;

import com.fiap.challenge.payment.core.domain.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Product {

	private Long id;
	private String name;
	private String description;
	private ProductCategory category;
	private BigDecimal price;
	private List<String> images;
	private Boolean active;

}
