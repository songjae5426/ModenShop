package com.songjae.modenshop.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ManagerCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		HttpSession session = request.getSession(false); // 세션인 없으면 null
		if (!(session.getAttribute("memberType").equals("manager"))) {
			// 이거 경로 확인해봐야함 컨트롤러 없음
			response.sendRedirect("/moden-shop/not-manager");
			return false;
		} else {
			return true;
		}
	}
}
