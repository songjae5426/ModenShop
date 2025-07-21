package com.songjae.modenshop.member.manager.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerEmailChangeInternalDto {
	private final long id;
	private final String email;
	
}
