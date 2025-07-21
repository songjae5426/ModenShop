package com.songjae.modenshop.member.manager.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PasswordCheckInternalDto {
	private final long managerMemberId;
	private final String password;
}
