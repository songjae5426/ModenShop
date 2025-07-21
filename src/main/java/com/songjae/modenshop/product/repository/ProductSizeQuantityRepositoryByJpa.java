package com.songjae.modenshop.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.songjae.modenshop.product.domain.ProductSizeQuantity;
import com.songjae.modenshop.product.dto.ProductSizeQuantityDetailPageItemDto;
import com.songjae.modenshop.product.dto.ProductSizeQuantityManageItemDto;

public interface ProductSizeQuantityRepositoryByJpa extends JpaRepository<ProductSizeQuantity, Long>{
	// 제품id를 입력받아 해당하는 행 모두 가져오기
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductSizeQuantityManageItemDto(id, size, quantity) FROM ProductSizeQuantity WHERE productId = :productId")
	public List<ProductSizeQuantityManageItemDto> findProductSizeQuantityManageItemDtoByProductId(@Param("productId") long productId);
	
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductSizeQuantityDetailPageItemDto(id, size, quantity) FROM ProductSizeQuantity WHERE productId = :productId")
	public List<ProductSizeQuantityDetailPageItemDto> findProductSizeQuantityDetailPageItemDtoByProductId(@Param("productId") long productId);

	// 제품id와 연관된 행 모두 삭제
	@Modifying
	@Query("DELETE FROM ProductSizeQuantity WHERE productId = :productId")
	public int deleteProductSizeQuantityByProductId(@Param("productId") long productId);
	
	public int findQuantityByProductIdAndSize(long productId, String size);
}
