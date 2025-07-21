package com.songjae.modenshop.product.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductCategoryPageDto {
	private final List<ProductCategoryPageItemDto> productCategoryItemDtoList;
	private final boolean hasProduct;
	private final String category;
}
