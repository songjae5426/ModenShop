package com.songjae.modenshop.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewUploadResponse {
	private final boolean success;
	private final ReviewCardDto reviewCardDto;
}
