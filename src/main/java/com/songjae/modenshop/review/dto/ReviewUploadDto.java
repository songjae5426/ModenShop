package com.songjae.modenshop.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
public class ReviewUploadDto {
	private final long productId;
	private final long generalMemberId;
	private final String reviewTitle;
	private final String reviewText;
	private final byte point;
}
