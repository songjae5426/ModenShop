package com.songjae.modenshop.product.dto;

import com.songjae.modenshop.product.domain.ProductSizeQuantity.Size;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductSizeQuantityDetailPageItemDto {
	private final long id;
	private final Size size;
	private final long quantity;
}
