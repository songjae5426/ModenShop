package com.songjae.modenshop.member.manager.service;


import org.springframework.stereotype.Service;

import com.songjae.modenshop.member.manager.domain.ShopInfo;
import com.songjae.modenshop.member.manager.dto.internal.ShopInfoAddressChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.ShopInfoPhoneNumberChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.response.ManagerInfoChangeResponseDto.ChangeCode;
import com.songjae.modenshop.member.manager.repository.ShopInfoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShopInfoService {
	private final ShopInfoRepository shopInfoRepository;
	
	
	// shopId와 phoneNumber를 입력받아 고객센터 전화번호 업데이트
	public ChangeCode changeShopPhoneNumber(ShopInfoPhoneNumberChangeInternalDto shopInfoPhoneNumberChangeInternalDto) {
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setId(shopInfoPhoneNumberChangeInternalDto.getShopInfoId());
		shopInfo.setShopPhoneNumber(shopInfoPhoneNumberChangeInternalDto.getPhoneNumber());
		int updateRow = shopInfoRepository.updatePhoneNumberById(shopInfo);
		if(updateRow == 1) {
			return ChangeCode.SUCCESS;
		}
		return ChangeCode.PHONE_NUMBER_CHANGE_ERROR;
	}
	
	// shopId와 주소 입력받아 고객센터 주소 업데이트
	public ChangeCode changeShopAddress(ShopInfoAddressChangeInternalDto shopAddressChangeInternalDto) {
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setId(shopAddressChangeInternalDto.getShopInfoId());
		shopInfo.setShopPostCode(shopAddressChangeInternalDto.getPostCode());
		shopInfo.setShopAddress(shopAddressChangeInternalDto.getAddress());
		shopInfo.setShopDetailAddress(shopAddressChangeInternalDto.getDetailAddress());
		shopInfo.setShopExtraAddress(shopAddressChangeInternalDto.getExtraAddress());
		
		int updateRow = shopInfoRepository.updateAddressById(shopInfo);
		if(updateRow == 1) {
			return ChangeCode.SUCCESS;
		}
		return ChangeCode.ADDRESS_CHANGE_ERROR;
	}
	
}
