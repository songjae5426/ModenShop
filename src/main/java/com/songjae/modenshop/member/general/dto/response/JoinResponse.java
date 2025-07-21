package com.songjae.modenshop.member.general.dto.response;

import com.songjae.modenshop.member.general.dto.JoinDuplicationCheckInternalDto.DuplicationName;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinResponse {
	private final boolean joinSuccess;
	private final JoinFailCause joinFailCause;
	private final DuplicationName duplicationName;
	
	public enum JoinFailCause{
		DUPLICATION, 
		JOIN_ERROR
	}
}
