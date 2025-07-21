package com.songjae.modenshop.cart.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.songjae.modenshop.cart.dto.response.ProductAndCartFlatDto;

@Mapper
public interface CartRepositoryByMybatise {
	public List<ProductAndCartFlatDto> selectProductAndCartFlatDtoByGenearMemberId(@Param("generalMemberId") long generalMemberId);
	
}
