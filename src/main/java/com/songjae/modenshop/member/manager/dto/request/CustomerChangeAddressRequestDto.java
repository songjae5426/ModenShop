package com.songjae.modenshop.member.manager.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerChangeAddressRequestDto {
	private final String postCode;
	private final String address;
	private final String detailAddress;
	private final String extraAddress;
	
	@JsonCreator
	public CustomerChangeAddressRequestDto(
	        @JsonProperty("postCode") String postCode,
	        @JsonProperty("address") String address,
	        @JsonProperty("detailAddress") String detailAddress,
	        @JsonProperty("extraAddress") String extraAddress) {
	    this.postCode = postCode;
	    this.address = address;
	    this.detailAddress = detailAddress;
	    this.extraAddress = extraAddress;
	}
}
