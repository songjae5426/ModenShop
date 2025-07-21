package com.songjae.modenshop.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/moden-shop")
@Controller
public class HomeController {


	
	// 홈 화면
	@GetMapping("/home")
	public String showHomePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false); 
		String memberType = "notMember";
		if(session != null) {
			String sessionMemberType = (String)session.getAttribute("memberType");
			if (sessionMemberType != null) {
	            memberType = sessionMemberType;
	        }
		}
		switch(memberType) {
			case "general":
				model.addAttribute("memberType", "general");
				break;
			case "manager":
				model.addAttribute("memberType", "manager");
				break;
			case "notMember":
				model.addAttribute("memberType", "notMember");
				break;
		}
		model.addAttribute("pageType", "home");
		return "pages/basic/home";
	}
	
	
	
	
	
	
	
	
}
