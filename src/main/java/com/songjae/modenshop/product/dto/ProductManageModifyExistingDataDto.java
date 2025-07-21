package com.songjae.modenshop.product.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductManageModifyExistingDataDto {
	private final ProductManageModifyExistingDataItemDto productManageModifyExistingDataItemDto;
	private final List<ProductSizeQuantityManageItemDto> productSizeQuantityManageItemDtoList;
}
