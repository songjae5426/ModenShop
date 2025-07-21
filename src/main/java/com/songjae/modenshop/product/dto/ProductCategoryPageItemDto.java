package com.songjae.modenshop.product.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductCategoryPageItemDto {
	private final long id;
	private final String productName;
	private final int regularPrice;
	private final int discountRate;
	private final int salesPrice;
	private final String productMainImg;
}
