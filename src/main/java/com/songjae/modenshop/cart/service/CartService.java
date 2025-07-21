//package com.songjae.modenshop.cart.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.songjae.modenshop.cart.domain.Cart;
//import com.songjae.modenshop.cart.dto.request.CartAddRequestDto;
//import com.songjae.modenshop.cart.dto.response.CartPageItemDto;
//import com.songjae.modenshop.cart.dto.response.ProductAndCartFlatDto;
//import com.songjae.modenshop.cart.repository.CartRepositoryByJpa;
//import com.songjae.modenshop.cart.repository.CartRepositoryByMybatise;
//import com.songjae.modenshop.product.dto.ProductSizeQuantityDetailPageItemDto;
//import com.songjae.modenshop.product.service.ProductSizeQuantityService;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class CartService {
//	private final CartRepositoryByJpa cartRepositoryByJpa;
//	private final CartRepositoryByMybatise cartRepositoryByMybatise;
//	private final ProductSizeQuantityService productSizeQuantityService;
//	
//	// 장바구니 상품 추가
//	public boolean addCart(CartAddRequestDto cartAddRequestDto) {
//		Cart cart = Cart.builder()
//				.generalMemberId(cartAddRequestDto.getGeneralMemberId())
//				.productId(cartAddRequestDto.getProductId())
//				.selectedSize(cartAddRequestDto.getSelectedSize())
//				.selectedQuantity(cartAddRequestDto.getSelectedQuantity())
//				.salesPrice(cartAddRequestDto.getSalesPrice())
//				.build();
//		 cart = cartRepositoryByJpa.save(cart);
//		 return cart != null;
//	}
//	
//	// 맴버id를 받아 해당 맴버의 장바구니 정보 가져오기
//	@Transactional
//	public List<CartPageItemDto> getCartItem(long generalMemberId) {
//		List<CartPageItemDto> cartPageItemDtoList = new ArrayList<>();
//		List<ProductAndCartFlatDto> productAndCartFlatDtoList = 
//				cartRepositoryByMybatise.selectProductAndCartFlatDtoByGenearMemberId(generalMemberId);
//		for(ProductAndCartFlatDto productAndCartFlatDto : productAndCartFlatDtoList) {
//			long productId = productAndCartFlatDto.getProductId();
//			int cartPrice = productAndCartFlatDto.getCartPrice();
//			int nowPrice = productAndCartFlatDto.getSalesPrice();
//			int nowQuantity = productSizeQuantityService.getQuantityByProductIdAndSize(productId, productAndCartFlatDto.getSelectedSize());
//			int totalPrice = nowPrice * nowQuantity;
//			
//			String priceState = null;
//			if(cartPrice == nowPrice) {
//				priceState = "same";
//			}else if(cartPrice > nowPrice) {
//				priceState = "down";
//			}else if(cartPrice < nowPrice) {
//				priceState = "up";
//			}
//			
//			int selectedQuantity = productAndCartFlatDto.getSelectedQuantity();
//			String quantityState = null;
//			if(selectedQuantity == nowQuantity) {
//				quantityState = "same";
//			}else if(selectedQuantity > nowQuantity) {
//				quantityState = "down";
//			}
//			CartPageItemDto cartPageItemDto = CartPageItemDto.builder()
//					.productId(productId)
//					.productName(productAndCartFlatDto.getProductName())
//					.productMainImg(productAndCartFlatDto.getProductMainImg())
//					.size(productAndCartFlatDto.getSelectedSize())
//					.selectedQuantity(productAndCartFlatDto.getSelectedQuantity())
//					.quantity(nowQuantity)
//					.totalPrice(totalPrice)
//					.priceState(priceState)
//					.quantityState(quantityState)
//					.build();
//			cartPageItemDtoList.add(cartPageItemDto);
//		}
//		
//		
//		
//	}
//}
