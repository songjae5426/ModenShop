//package com.songjae.modenshop.cart;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.songjae.modenshop.cart.dto.request.CartAddRequestDto;
//import com.songjae.modenshop.cart.service.CartService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RequestMapping("/moden-shop")
//@RestController
//public class CartRestController {
//
//	private final CartService cartService;
//
//
//	
//	
//	// 장부구니에 상품 추가
//	@PostMapping("/general/cart/session/check")
//	public Map<String, Boolean> addCart(@RequestBody CartAddRequestDto cartAddRequestDto, HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		long memberId = (long)session.getAttribute("memberId");
//		cartAddRequestDto = cartAddRequestDto.toBuilder()
//				.generalMemberId(memberId)
//				.build();
//		boolean success = cartService.addCart(cartAddRequestDto);
//		Map<String, Boolean> resultMap = new HashMap<>();
//		resultMap.put("success", success);
//		return resultMap;
//	}
//	
//	
//	
//	
//}
