package com.songjae.modenshop.member.general.dto.response;

import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto.LoginCheckState;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginCheckResponseDto {
	private final LoginCheckState loginCheckState; 
}
