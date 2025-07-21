package com.songjae.modenshop.cart.dto.request;

import com.songjae.modenshop.cart.domain.Cart.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Getter
public class CartAddRequestDto {
	private final long generalMemberId;
	private final long productId;
	private final Size selectedSize;
	private final int selectedQuantity;
	private final int salesPrice;
	
}
