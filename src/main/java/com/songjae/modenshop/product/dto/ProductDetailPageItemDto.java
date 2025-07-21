package com.songjae.modenshop.product.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDetailPageItemDto {
	private final long id;
	private final String productName;
	private final int regularPrice;
	private final int discountRate;
	private final int salesPrice;
	private final int deliveryCost;
	private final String productMainImg;
	private final String productExplanationHtml;
}
