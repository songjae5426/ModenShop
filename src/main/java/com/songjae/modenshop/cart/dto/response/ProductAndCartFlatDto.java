package com.songjae.modenshop.cart.dto.response;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAndCartFlatDto {
	private long productId;
	private String productName;
	private int salesPrice;
	private String productMainImg;
	private int deliveryCost;
	private String selectedSize;
	private int selectedQuantity;
	private int cartPrice;
}
