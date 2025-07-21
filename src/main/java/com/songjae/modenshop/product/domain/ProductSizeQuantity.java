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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "`product_size_quantity`")
@Entity
public class ProductSizeQuantity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private long productId;
	// 상수 설정 EnumType.STRING => 문자열 그대로 , EnumType.ORDINAL=> enum의 상수 순서대로 0부터 숫자로 대체됨
	@Enumerated(EnumType.STRING)
	private Size size;
	private long quantity;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public enum Size {
	    S, M, L
	}
}



