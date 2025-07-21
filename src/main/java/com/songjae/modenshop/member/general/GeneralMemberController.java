package com.songjae.modenshop.member.general;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/moden-shop")
@Controller
public class GeneralMemberController {
	// 일반 회원 마이페이지
	@GetMapping("/my-page/session/check")
	public String showMyPage(Model model) {
		model.addAttribute("pageType", "myPage");
		return "pages/basic/myPage";
	}
	
}
