package com.songjae.modenshop.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/moden-shop")
@Controller
public class MemberController {
	
	// 로그인 페이지 로딩
	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute("pageType", "login");
		return "pages/basic/login";
	}
	
	// 회원가입 페이지 로딩
	@GetMapping("/join")
	public String showjoinPage(Model model) {
		model.addAttribute("pageType", "join");
		return "pages/basic/join";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {
		// 인자로 HttpSession을 받으면 세션이 없으면 세션을 생성하기 때문에 request를 사용
		HttpSession session = request.getSession(false); // 기존 세션 가져오기 (없으면 null)
		if (session != null) {
			session.invalidate(); // 세션 완전 제거
			return "redirect:/moden-shop/home";
		}else {
			return "redirect:/moden-shop/home";
		}
	}
	
	
	
	
	
	
}
