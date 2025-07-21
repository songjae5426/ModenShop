package com.songjae.modenshop.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class GeneralCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ajax요청이면 헤더에 (X-Requested-With: XMLHttpRequest)이 담겨서 넘어온다
		// 일반 요청 (a태그, form, 주소창)은 X-Requested-With라는 키값이 아예 없다
		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		HttpSession session = request.getSession(false);	// 세션인 없으면 null
		// 세션이 없거나 만료되었을때
		if (!(session.getAttribute("memberType").equals("generalMember"))) {
			if(isAjax) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403에러 => 권한 부족
			}else {
				response.sendRedirect("/moden-shop/notGeneral");
			}
			return false; // 요청 흐름 중단 다음에 실행될 인터셉터로도 안감
		}
		return true; // 통과
	}
}
