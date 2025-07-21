package com.songjae.modenshop.member.manager.dto.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ManagerIdentityVerificationReqeustDto {
	private final String password;
	
	@JsonCreator
	public ManagerIdentityVerificationReqeustDto(@JsonProperty("password") String password){
		this.password = password;
	}
}
