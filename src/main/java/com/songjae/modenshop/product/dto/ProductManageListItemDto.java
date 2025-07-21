package com.songjae.modenshop.product.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductManageListItemDto {
	private final long id;
	private final String productName;
}
