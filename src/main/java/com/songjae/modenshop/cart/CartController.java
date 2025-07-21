package com.songjae.modenshop.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/moden-shop")
@Controller
public class CartController {
	// 장바구니 화면
	@GetMapping("general/cart/session/check")
	public String showCartPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long memberId = (long)session.getAttribute("memberId");
		
		model.addAttribute("pageType", "cart");
		return "pages/basic/cart";
	}
}
