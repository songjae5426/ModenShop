package com.songjae.modenshop.review.dto;


import com.songjae.modenshop.member.general.dto.GeneralMemberReviewCardItemDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewCardDto {
	private final ReviewCardItemDto reviewCardItemDto;
	private final GeneralMemberReviewCardItemDto generalMemberReviewCardItemDto;
}
