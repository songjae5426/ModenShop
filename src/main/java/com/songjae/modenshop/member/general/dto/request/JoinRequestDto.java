package com.songjae.modenshop.member.general.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinRequestDto {
	private final String email;
	private final String password;
	private final String name;
	private final String nickName;
	private final String phoneNumber;
	private final String postCode;
	private final String address;
	private final String detailAddress;
	private final String extraAddress;
	
//	@JsonCreator는 어떤 생성자를 사용해 매핑할지 지정
//	@JsonProperty는 JSON 키를 어떤 파라미터에 넣을지 명시
	@JsonCreator
	public JoinRequestDto(
			@JsonProperty("email") String email,
			@JsonProperty("password") String password,
			@JsonProperty("name") String name,
			@JsonProperty("nickName") String nickName,
			@JsonProperty("phoneNumber") String phoneNumber,
			@JsonProperty("postCode") String postCode,
			@JsonProperty("address") String address,
			@JsonProperty("detailAddress") String detailAddress,
			@JsonProperty("extraAddress") String extraAddress
	) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.postCode = postCode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.extraAddress = extraAddress;
	}
}
