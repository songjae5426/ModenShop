package com.songjae.modenshop.member.manager.dto.internal;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerChangeAddressInternalDto {
	private final long managerMemberId;
	private final String postCode;
	private final String address;
	private final String detailAddress;
	private final String extraAddress;
}
