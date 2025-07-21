package com.songjae.modenshop.member.manager.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerIdentityVerificationResponseDto {
	private final boolean success;
}
