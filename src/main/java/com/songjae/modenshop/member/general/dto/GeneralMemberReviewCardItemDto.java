package com.songjae.modenshop.member.general.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralMemberReviewCardItemDto {
	private final long id;
	private final String nickName;
	private final String profileImg;
}
