package com.songjae.modenshop.product.dto;


import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductMangerCardDto {
	private final ProductManageCardItemDto productManageCardItemDto;
	private final List<ProductSizeQuantityManageItemDto> productSizeQuantityManageItemDtoList;
}
