package com.songjae.modenshop.member.manager.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerCenterPhoneNumberChangeInternalDto {
	private final long managerMemberId;
	private final String phoneNumber;
}
