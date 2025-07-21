package com.songjae.modenshop.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.songjae.modenshop.product.domain.ProductSizeQuantity;
import com.songjae.modenshop.product.dto.ProductSizeQuantityDetailPageItemDto;
import com.songjae.modenshop.product.dto.ProductSizeQuantityManageItemDto;
import com.songjae.modenshop.product.repository.ProductSizeQuantityRepositoryByJpa;
import com.songjae.modenshop.review.repository.ReviewRepositoryByJpa;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductSizeQuantityService {

	private final ProductSizeQuantityRepositoryByJpa productSizeQuantityRepositoryByJpa;

	
	// 제품id,사이즈,수량을 입력받아 추가
	public boolean addProductSizeQuantity(ProductSizeQuantity productSizeQuantity) {
		productSizeQuantity = productSizeQuantityRepositoryByJpa.save(productSizeQuantity);
		if(productSizeQuantity != null) {
			return true;
		}
		return false;
	}
	
	public boolean updateProductSizeQuantity(ProductSizeQuantity productSizeQuantity) {
		productSizeQuantity = productSizeQuantityRepositoryByJpa.save(productSizeQuantity);
		if(productSizeQuantity != null) {
			return true;
		}
		return false;
	}
	
	// 제품 id를 입력받아 해당 id인 행들 모두 가져오기(관리자)
	public List<ProductSizeQuantityManageItemDto> getProductSizeQuantityManageItemDtoByProductId(long productId){
		return productSizeQuantityRepositoryByJpa.findProductSizeQuantityManageItemDtoByProductId(productId);
	}
	
	// 제품 id를 입력받아 해당 id인 행들 모두 가져오기(상세페이지)
	public List<ProductSizeQuantityDetailPageItemDto> getProductSizeQuantityDetailPageItemDtoByProductId(long productId){
		return productSizeQuantityRepositoryByJpa.findProductSizeQuantityDetailPageItemDtoByProductId(productId);
	}
	
	// 제품id로 해당 제품 사이즈 모두 삭제
	public boolean deleteProductSizeQuantityByPorductId(long productId) {
		int deleteRow = productSizeQuantityRepositoryByJpa.deleteProductSizeQuantityByProductId(productId);
		if(deleteRow == 3) {
			return true;
		}else {
			return false;
		}
	}
	
	// 제품Id와 사이즈를 입력 받아 해당 제품사이즈의 수량 
	public int getQuantityByProductIdAndSize(long productId, String size) {
		return productSizeQuantityRepositoryByJpa.findQuantityByProductIdAndSize(productId, size);
	}
	
	
	
}
