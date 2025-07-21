package com.songjae.modenshop.product.dto;



import com.songjae.modenshop.product.domain.Product.ProductCategory;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductManageModifyExistingDataItemDto {
	private final String productName;
	private final ProductCategory productCategory;
	private final int regularPrice;
	private final int salesPrice;
	private final int deliveryCost;
	private final String productMainImg;
	private final String productExplanationHtml;
}
