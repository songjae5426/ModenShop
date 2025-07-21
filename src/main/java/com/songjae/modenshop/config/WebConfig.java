package com.songjae.modenshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.songjae.modenshop.interceptor.GeneralCheckInterceptor;
import com.songjae.modenshop.interceptor.LoginCheckInterceptor;
import com.songjae.modenshop.interceptor.ManagerCheckInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final LoginCheckInterceptor loginCheckInterceptor;
	private final ManagerCheckInterceptor managerCheckInterceptor;
	private final GeneralCheckInterceptor generalCheckInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 로그인 체크 인터셉터
		registry.addInterceptor(loginCheckInterceptor)
				.addPathPatterns("/**/session/check") // 적용할 url
				.excludePathPatterns("/**/login", "/**/join", "/**/home", "/css/**", "/img/**", "/icon/**"); // 제외할 url
		// 메니저 체크 인터셉터
		registry.addInterceptor(managerCheckInterceptor)
		.addPathPatterns("/**/manager/**/session/check"); // 적용할 url
		// 일반회원 체크 인터셉터
		registry.addInterceptor(generalCheckInterceptor)
		.addPathPatterns("/**/general/**/session/check"); // 적용할 url
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/product/**")
	            .addResourceLocations("file:///C:/modenShop/product/");
	}
	
	
}
