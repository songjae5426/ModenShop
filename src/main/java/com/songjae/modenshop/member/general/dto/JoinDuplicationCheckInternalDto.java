package com.songjae.modenshop.member.general.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinDuplicationCheckInternalDto {
	private final boolean duplication;
	private final DuplicationName duplicationName;
	
	public enum DuplicationName{
		EMAIL,
		NICK_NAME,
		PHONE_NUMBER
	}
}
