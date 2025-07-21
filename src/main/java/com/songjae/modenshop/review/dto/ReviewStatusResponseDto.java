package com.songjae.modenshop.review.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewStatusResponseDto {
	private final long reviewCount;
	private final boolean hasReview;
}
