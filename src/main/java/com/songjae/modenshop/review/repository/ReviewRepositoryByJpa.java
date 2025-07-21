package com.songjae.modenshop.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.songjae.modenshop.review.domain.Review;

public interface ReviewRepositoryByJpa extends JpaRepository<Review, Long> {
	// 제품 Id를 받아 해당 제품의 리뷰 갯수 반환
	public long countByProductId(long productId);
	
	@Modifying
	@Query("DELETE FROM Review WHERE productId = :productId")
	public void deleteReviewByProductId(@Param("productId") long productId);
	
	public boolean existsByProductId(long productId);
}
