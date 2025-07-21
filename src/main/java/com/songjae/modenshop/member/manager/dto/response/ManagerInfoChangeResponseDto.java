package com.songjae.modenshop.member.manager.dto.response;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ManagerInfoChangeResponseDto {
	private final boolean success;
	private final ChangeCode changeCode;
	
	public enum ChangeCode{
		SUCCESS,
		EMAIL_CHANGE_ERROR,
		PHONE_NUMBER_CHANGE_ERROR,
		PASSWORD_CHANGE_ERROR,
		NOW_PASSWORD_MISMATCH,
		ADDRESS_CHANGE_ERROR
	}
}
