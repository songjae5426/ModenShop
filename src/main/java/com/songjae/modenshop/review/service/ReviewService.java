package com.songjae.modenshop.review.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.songjae.modenshop.member.general.dto.GeneralMemberReviewCardItemDto;
import com.songjae.modenshop.member.general.service.GeneralMemberService;
import com.songjae.modenshop.review.domain.Review;
import com.songjae.modenshop.review.dto.ReviewCardDto;
import com.songjae.modenshop.review.dto.ReviewCardFlatDto;
import com.songjae.modenshop.review.dto.ReviewCardItemDto;
import com.songjae.modenshop.review.dto.ReviewUploadDto;
import com.songjae.modenshop.review.repository.ReviewRepositoryByJpa;
import com.songjae.modenshop.review.repository.ReviewRepositoryByMyBatis;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepositoryByJpa reviewRepositoryByJpa;
	private final ReviewRepositoryByMyBatis reviewRepositoryByMyBatis;
	private final GeneralMemberService generalMemberService;

	
	// 리뷰 갯수, 리뷰 존재여부 가져오기(상세페이지)
	public long getReviewCountByProductId(long productId) {
		return reviewRepositoryByJpa.countByProductId(productId);
	}
	//
	// 제품 id를 입력받아 해당 제품의 리뷰List,리뷰작성 회원 정보를 가져옴(상세페이지, 관리자페이지 통합 추후 분리)
	public List<ReviewCardDto> getReviewCardDtoListByProductId(long productId, int size, int page){
		int offset = size * (page - 1);
		List<ReviewCardDto> reviewCardDtoList = new ArrayList<>();
		List<ReviewCardFlatDto> reviewCardFlatDtoList = reviewRepositoryByMyBatis.selectReviewCardFlatDtoByProductId(productId, size, offset);
		for(ReviewCardFlatDto reviewCardFlatDto : reviewCardFlatDtoList) {
			ReviewCardItemDto reviewCardItemDto = ReviewCardItemDto.builder()
					.id(reviewCardFlatDto.getReviewId())
					.reviewTitle(reviewCardFlatDto.getReviewTitle())
					.reviewText(reviewCardFlatDto.getReviewText())
					.point(reviewCardFlatDto.getPoint())
					.reviewUpdatedAt(reviewCardFlatDto.getReviewUpdatedAt())
					.build();
			GeneralMemberReviewCardItemDto generalMemberReviewCardItemDto = GeneralMemberReviewCardItemDto.builder()
					.id(reviewCardFlatDto.getGeneralMemberId())
					.nickName(reviewCardFlatDto.getNickName())
					.profileImg(reviewCardFlatDto.getProfileImg())
					.build();
			ReviewCardDto reviewCardResponseDto = ReviewCardDto.builder()
					.generalMemberReviewCardItemDto(generalMemberReviewCardItemDto)
					.reviewCardItemDto(reviewCardItemDto)
					.build();
			reviewCardDtoList.add(reviewCardResponseDto);
		}
		return reviewCardDtoList;
	}
	
	
	
	
	
	// 리뷰 등록후 등록한 리뷰 카드 정보 가져오기
//	public ReviewCardDto addReview(ReviewUploadDto reviewUploadDto) {
//		Review review = Review.builder()
//				.productId(reviewUploadDto.getProductId())
//				.generalMemberId(reviewUploadDto.getGeneralMemberId())
//				.reviewTitle(reviewUploadDto.getReviewTitle())
//				.reviewText(reviewUploadDto.getReviewText())
//				.point(reviewUploadDto.getPoint())
//				.build();
//		review = reviewRepositoryByJpa.save(review);
//		ReviewCardItemDto reviewCardItemDto = ReviewCardItemDto.builder()
//				.id(review.getId())
//				.reviewTitle(review.getReviewTitle())
//				.reviewText(review.getReviewText())
//				.reviewUpdatedAt(review.getUpdatedAt())
//				.point(review.getPoint())
//				.build();
//		GeneralMemberReviewCardItemDto generalMemberReviewCardItemDto = generalMemberService.getGeneralMemberReviewCardItemDtoById(reviewUploadDto.getGeneralMemberId());
//		ReviewCardDto reviewCardResponseDto = ReviewCardDto.builder()
//				.reviewCardItemDto(reviewCardItemDto)
//				.generalMemberReviewCardItemDto(generalMemberReviewCardItemDto)
//				.build();
//		return reviewCardResponseDto;	
//	}
	
	// 리뷰id로 삭제 => 해당 id가 없으면 예외발섕X => 반환값 없음 
	public boolean deleteReview(long reviewId) {
		reviewRepositoryByJpa.deleteById(reviewId);
		return true;
	}
	
	// 해당 제품id와 같은 리뷰 행들 삭제
	public boolean deleteReviewByProductId(long productId) {
		reviewRepositoryByJpa.deleteReviewByProductId(productId);
		boolean reviewExists = reviewRepositoryByJpa.existsByProductId(productId);
		return !reviewExists;
	}
	
	
}
