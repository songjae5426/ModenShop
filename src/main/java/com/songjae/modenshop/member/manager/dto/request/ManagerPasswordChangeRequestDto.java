package com.songjae.modenshop.member.manager.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ManagerPasswordChangeRequestDto {
	private final String newPassword;
	private final String nowPassword;
	
	@JsonCreator
	public ManagerPasswordChangeRequestDto(
			@JsonProperty("newPassword") String newPassword,
			@JsonProperty("nowPassword") String nowPassword) {
		this.newPassword = newPassword;
		this.nowPassword = nowPassword;
	}
}
