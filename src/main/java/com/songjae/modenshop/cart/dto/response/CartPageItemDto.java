package com.songjae.modenshop.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CartPageItemDto {
	private final long productId;
	private final String productName;
	private final String productMainImg;
	private final String size;
	private final int selectedQuantity;
	private final int quantity;
	private final int totalPrice;
	private final String priceState;
	private final String quantityState;
}
