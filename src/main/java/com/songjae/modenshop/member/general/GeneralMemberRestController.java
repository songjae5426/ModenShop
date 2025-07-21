package com.songjae.modenshop.member.general;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto.LoginCheckState;
import com.songjae.modenshop.member.general.dto.request.JoinRequestDto;
import com.songjae.modenshop.member.general.dto.request.LoginRequestDto;
import com.songjae.modenshop.member.general.dto.response.JoinResponse;
import com.songjae.modenshop.member.general.dto.response.LoginCheckResponseDto;
import com.songjae.modenshop.member.general.service.GeneralMemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/moden-shop")
@RequiredArgsConstructor
@RestController
public class GeneralMemberRestController {
	private final GeneralMemberService generalMemberService;

	// 일반회원 회원가입
	@PostMapping("/general/Member/join")
	public JoinResponse generalMemberJoin(@RequestBody JoinRequestDto joinRequestDto) {
		return generalMemberService.generalMemberJoin(joinRequestDto);
	}

	// 일반회원 로그인
	@PostMapping("/general/Member/login")
	public LoginCheckResponseDto generalMemberLogin(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
		LoginCheckInternalDto loginCheckInternalDto = generalMemberService.generalMemberLoginCheck(loginRequestDto.getEmail(), loginRequestDto.getPassword());
		if(loginCheckInternalDto.getLoginCheckState() == LoginCheckState.SUCCESS) {
			session.setAttribute("memberType", "general");
			session.setAttribute("memberId", loginCheckInternalDto.getId());
		}
		return LoginCheckResponseDto.builder()
				.loginCheckState(loginCheckInternalDto.getLoginCheckState())
				.build();
	}



}
