package com.songjae.modenshop.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDeleteResponse {
	private final boolean success;
	private final long deleteReviewId;
}
