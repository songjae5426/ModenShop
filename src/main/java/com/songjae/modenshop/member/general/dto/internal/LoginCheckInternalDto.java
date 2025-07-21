package com.songjae.modenshop.member.general.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginCheckInternalDto {
	private final LoginCheckState loginCheckState; 
	private final long id;
	
	public enum LoginCheckState{
		EMAIL_NOT_FOUND,
		INVALID_PASSWORD,
		SUCCESS
	}
}
