package com.songjae.modenshop.product.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
	private long id;
	private ProductCategory productCategory;
	private String productName;
	private int regularPrice;
	private int discountRate;
	private int salesPrice;
	private int deliveryCost;
	private String productMainImg;
	private String productExplanationHtml;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// 할인률 계산
	@PrePersist	// insert되기 전에 실행
	@PreUpdate	// update되기 전에 실행
	public void updateDiscountRate() {
		if (regularPrice <= 0) {
			this.discountRate = 0; // 0으로 나누는 오류 방지
		}
	    double discount = ((double)(regularPrice - salesPrice) / regularPrice) * 100;
	    this.discountRate = (int)Math.round(discount); // 소수점 반올림해서 정수로 반환
	}
	
	public enum ProductCategory{
		OUTER, TOP, SHIRT, PANTS, ACCESSORY
	}

}
