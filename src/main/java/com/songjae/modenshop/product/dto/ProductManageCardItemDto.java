package com.songjae.modenshop.product.dto;

import com.songjae.modenshop.product.domain.Product.ProductCategory;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductManageCardItemDto {
	private final long id;
	private final String productName;
	private final ProductCategory productCategory;
	private final int regularPrice;
	private final int discountRate;
	private final int salesPrice;
	private final int deliveryCost;
	private final String productMainImg;
}
