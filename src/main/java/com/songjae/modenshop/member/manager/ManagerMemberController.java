package com.songjae.modenshop.member.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/moden-shop")
@Controller
public class ManagerMemberController {
	// 관리자 페이지 이동
	@GetMapping("/manager-page")
	public String showManagerPage () {
		return "pages/spa/managerPage";
	}
	
}
