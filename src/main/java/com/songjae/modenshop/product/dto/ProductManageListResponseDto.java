package com.songjae.modenshop.product.dto;

import java.util.List;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductManageListResponseDto {
	private final boolean isEmptyList; 
	private final List<ProductManageListItemDto> productManageListItemDtoList;
}
