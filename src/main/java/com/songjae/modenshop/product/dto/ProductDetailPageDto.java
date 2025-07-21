package com.songjae.modenshop.product.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDetailPageDto {
	private final ProductDetailPageItemDto productDetailPageItemDto;
	private final List<ProductSizeQuantityDetailPageItemDto> productSizeQuantityDetailPageItemDtoList;
}
