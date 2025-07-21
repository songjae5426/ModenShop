package com.songjae.modenshop.member.manager.repository;

import org.apache.ibatis.annotations.Mapper;

import com.songjae.modenshop.member.manager.domain.ShopInfo;


@Mapper
public interface ShopInfoRepository{
	// shopInfoId를 입력받아 해당 행 전화번호 수정
	public int updatePhoneNumberById(ShopInfo shopInfo);
	
	// shopInfoId를 입력받아 해당 행 주소 수정
	public int updateAddressById(ShopInfo shopInfo);
	
}
