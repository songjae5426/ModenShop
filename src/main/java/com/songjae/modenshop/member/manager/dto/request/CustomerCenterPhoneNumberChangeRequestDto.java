package com.songjae.modenshop.member.manager.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerCenterPhoneNumberChangeRequestDto {
	private final String phoneNumber;
	
	@JsonCreator
	public CustomerCenterPhoneNumberChangeRequestDto(@JsonProperty("phoneNumber") String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
