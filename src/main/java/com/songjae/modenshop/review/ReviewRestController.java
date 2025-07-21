package com.songjae.modenshop.review;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.songjae.modenshop.review.dto.ReviewCardDto;
import com.songjae.modenshop.review.dto.ReviewStatusResponseDto;
import com.songjae.modenshop.review.dto.ReviewUploadDto;
import com.songjae.modenshop.review.dto.ReviewUploadResponse;
import com.songjae.modenshop.review.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/moden-shop")
@RestController
public class ReviewRestController {
	
	private final ReviewService reviewService;
	
	// 리뷰 상태 (상세페이지)
	@GetMapping("/review/count")
	public ReviewStatusResponseDto getReviewStatusResponseDtoByProductId(
			@RequestParam("productId") long productId){
		long reviewCount = reviewService.getReviewCountByProductId(productId);
		boolean hasReview = reviewCount > 0;
		return ReviewStatusResponseDto.builder()
				.hasReview(hasReview)
				.reviewCount(reviewCount)
				.build();
	}
	
	// 리뷰 리스트 무한스트롤 (상세페이지)
	@GetMapping("/review/list")
	public List<ReviewCardDto> getReviewCardResponseDtoListByProductId(
			@RequestParam("productId") long productId, 
			@RequestParam("size") int size,
			@RequestParam("page") int page){
		List<ReviewCardDto> reviewCardDtoList = reviewService.getReviewCardDtoListByProductId(productId, size, page);
		return reviewCardDtoList;
	}
	
	
	
	
//	// 리뷰 쓰기 일반회원만 가능하게 인터셉터 만들기
//	@PostMapping("general/review/write/session/check")
//	public ReviewUploadResponse reviewWrite(@RequestBody ReviewUploadDto reviewUploadDto, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		long generalMemberId = (long)session.getAttribute("memberId");
//		reviewUploadDto = reviewUploadDto.toBuilder().generalMemberId(generalMemberId).build();
//		ReviewCardDto reviewCardDto = reviewService.addReview(reviewUploadDto);
//		boolean success = reviewCardDto != null;
//		return ReviewUploadResponse.builder()
//				.success(success)
//				.reviewCardDto(reviewCardDto)
//				.build();
//	}
	
	
	// 관리자 리뷰 삭제 
	@GetMapping("/manager/review/delete/session/check")
	public Map<String, Boolean> deleteRivew(@RequestParam("reviewId") long reviewId){
		boolean deleteSuccess = reviewService.deleteReview(reviewId);
		Map<String, Boolean> resultMap = new HashMap<>();
		resultMap.put("success", deleteSuccess);
		return resultMap;
	}
	
	
	
}
