package com.songjae.modenshop.member.manager.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PasswordChangeInternalDto {
	private final long managerId;
	private final String newPassword;
	private final String nowPassword;
}
