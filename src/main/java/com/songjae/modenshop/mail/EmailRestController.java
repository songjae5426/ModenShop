package com.songjae.modenshop.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.songjae.modenshop.common.util.AuthCodeGenerator;
import com.songjae.modenshop.mail.service.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/moden-shop")
@RequiredArgsConstructor
@RestController
public class EmailRestController {
	private final EmailService emailService;
	
	// 이메일 발송
	@PostMapping("/email/auth-code/send")
	public Map<String, Object> emailAuthCodeSend(
			@RequestParam("email") String email, 
			HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		String authCode = AuthCodeGenerator.authCodeGenerator(10);
		String authCodeHtml = emailService.authCodeHtml(authCode);
		if(emailService.sendHtmlMail(email, authCodeHtml)) {
			session.setAttribute("authCode", authCode);
			resultMap.put("success", true);
			return resultMap;
		}else {
			resultMap.put("success", false);
			return resultMap;
		}
	}
	
	// 이메일 인증번호 체크
	@PostMapping("/email/auth-code/check")
	public Map<String, Object> emailAuthCodeCheck(@RequestParam("authCode") String authCode, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpSession session = request.getSession(false);
		String sessionAuthCode = (String) session.getAttribute("authCode");
		if(emailService.emailAuthCodeCheck(authCode, sessionAuthCode)) {
			session.removeAttribute("authCode");	// authCode속성 삭제
			resultMap.put("same", true);
			return resultMap;
		}else {
			resultMap.put("same", false);
			return resultMap;
		}
	}
}
