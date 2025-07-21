package com.songjae.modenshop.product.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.songjae.modenshop.product.domain.Product;
import com.songjae.modenshop.product.dto.ProductCategoryPageItemDto;
import com.songjae.modenshop.product.dto.ProductDetailPageItemDto;
import com.songjae.modenshop.product.dto.ProductManageCardItemDto;
import com.songjae.modenshop.product.dto.ProductManageListItemDto;
import com.songjae.modenshop.product.dto.ProductManageModifyExistingDataItemDto;

public interface ProductRepositoryByJpa extends JpaRepository<Product, Long>{
	
	// 관리자페이지 제품 리스트 검색 조회(관리자)
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductManageListItemDto(id, productName) FROM Product WHERE productName LIKE %:productName%")
	public List<ProductManageListItemDto> findProductManageListItemDtoByProductName(@Param("productName") String productName);
	
	// 카테고리 페이지 제품 리스트 조회(카테고리)
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductCategoryPageItemDto(id, productName, regularPrice, discountRate, salesPrice, productMainImg) FROM Product WHERE productCategory = :productCategory ORDER BY updatedAt DESC")
	public Page<ProductCategoryPageItemDto> findProductCategoryItemDtoPageByProductCategoryOrderByUpdatedAt(@Param("productCategory")Product.ProductCategory productCategory, Pageable pageable);
	
	// 관리자페이지 제품 리스트 조회(관리자)
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductManageListItemDto(id, productName) FROM Product")
	public List<ProductManageListItemDto> findProductManageListItemDtoList();
	
	// 제품정보 카드 조회(관리자)
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductManageCardItemDto(id, productName, productCategory, regularPrice, discountRate, salesPrice, deliveryCost, productMainImg) FROM Product WHERE id = :productId")
	public ProductManageCardItemDto findProductManageCardItemDtoByProductId(@Param("productId") long productId);
	
	// 제품수정 기존정보 조회(관리자)
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductManageModifyExistingDataItemDto(productName, productCategory, regularPrice, salesPrice, deliveryCost, productMainImg, productExplanationHtml) FROM Product WHERE id = :productId")
	public ProductManageModifyExistingDataItemDto findProductManageModifyExistingDataItemDtoByProductId(@Param("productId") long productId);

	// 상세페이지 제품 정보 조회(상세페이지)
	@Query("SELECT new com.songjae.modenshop.product.dto.ProductDetailPageItemDto(id, productName, regularPrice, discountRate, salesPrice, deliveryCost, productMainImg, productExplanationHtml) FROM Product WHERE id = :productId")
	public ProductDetailPageItemDto findProductDetailPageItemDtoByProductId(@Param("productId") long productId);
	
}
