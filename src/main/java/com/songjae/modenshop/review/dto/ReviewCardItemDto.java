package com.songjae.modenshop.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewCardItemDto {
	private final long id;
	private final String reviewTitle;
	private final String reviewText;
	private final byte point;
	// 날짜 데이터 포맷
	// @jsonFromat은 json으로 직렬화/역직렬화 할떄 포맷을 해준다
	// shape = JsonFormat.Shape.STRING => 문자열형태로 반환
	// pattern = "yyyy.MM.dd HH:mm" => 포맷 형식
	// timezone = "Asia/Seoul" => 시간대 기준
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
	private final LocalDateTime reviewUpdatedAt;
}
