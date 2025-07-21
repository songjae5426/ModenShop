package com.songjae.modenshop.member.manager.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ManagerEmailChangeRequestDto {
	private final String email;
	
	@JsonCreator
	public ManagerEmailChangeRequestDto(@JsonProperty("email") String email) {
		this.email = email;
	}
}
