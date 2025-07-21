package com.songjae.modenshop.review.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.songjae.modenshop.review.dto.ReviewCardFlatDto;

@Mapper
public interface ReviewRepositoryByMyBatis {
	public List<ReviewCardFlatDto> selectReviewCardFlatDtoByProductId(@Param("productId") long productId, @Param("size") int size, @Param("offset") int offset);
	
}
