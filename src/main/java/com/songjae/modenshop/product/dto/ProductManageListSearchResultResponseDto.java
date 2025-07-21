package com.songjae.modenshop.product.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductManageListSearchResultResponseDto {
	private final List<ProductManageListItemDto> productManageListItemDtoList;
	private final boolean serachResultEmpty;
}
