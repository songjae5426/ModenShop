package com.songjae.modenshop.review.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCardFlatDto {
	private long generalMemberId;
	private String nickName;
	private String profileImg;
	private long reviewId;
	private String reviewTitle;
	private String reviewText;
	private byte point;
	private LocalDateTime reviewUpdatedAt;
}
