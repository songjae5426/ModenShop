package com.songjae.modenshop.member.manager.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopInfoPhoneNumberChangeInternalDto {
	private final long shopInfoId;
	private final String phoneNumber;
}
