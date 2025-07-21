package com.songjae.modenshop.member.manager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.songjae.modenshop.common.util.BcryptHashingEncoder;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto.LoginCheckState;
import com.songjae.modenshop.member.manager.domain.ManagerMember;
import com.songjae.modenshop.member.manager.dto.internal.CustomerCenterPhoneNumberChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.CustomerChangeAddressInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.ManagerEmailChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.PasswordChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.PasswordCheckInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.ShopInfoAddressChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.ShopInfoPhoneNumberChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.response.ManagerInfoChangeResponseDto.ChangeCode;
import com.songjae.modenshop.member.manager.repository.ManagerMemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerMemberService {
	private final ShopInfoService shopInfoService;
	private final ManagerMemberRepository managerMemberRepository;


	// 이메일 중복 여부만 반환
	public boolean existsByEmail(String email) {
		return managerMemberRepository.existsByEmail(email);
	}


	// 로그인 체크
	public LoginCheckInternalDto managerMemberLoginCheck(String email, String password) {
		ManagerMember managerMember = managerMemberRepository.selectIdAndPasswordByEmail(email); // 해당 행이 없으면 null 반환
		if (managerMember == null) {
			return LoginCheckInternalDto.builder()
					.loginCheckState(LoginCheckState.EMAIL_NOT_FOUND)
					.build();
		}
		// 일치하면 true
		boolean passwordMatches = BcryptHashingEncoder.hashCheck(password, managerMember.getPassword());
		if (!passwordMatches) {
			return LoginCheckInternalDto.builder()
					.loginCheckState(LoginCheckState.INVALID_PASSWORD)
					.build();
		}
		return LoginCheckInternalDto.builder()
				.loginCheckState(LoginCheckState.SUCCESS)
				.id(managerMember.getId())
				.build();
	}
	
	// 관리자 이름만 가져오기
	public String getManagerName(long managerMemberId) {
		return managerMemberRepository.selectNameById(managerMemberId);
	}
	

	// 메니저 페이지 본인인증(비밀 번호 체크)
	public boolean passwordCheck(PasswordCheckInternalDto passwordCheckInternalDto) {
		String passwordHash = managerMemberRepository.selectPasswordById(passwordCheckInternalDto.getManagerMemberId());
		boolean passwordCheck = BcryptHashingEncoder.hashCheck(passwordCheckInternalDto.getPassword(), passwordHash);
		return passwordCheck;
	}

	
	// 메니저 이메일 변경
	public boolean changeEmail(ManagerEmailChangeInternalDto managerEmailChangeInternalDto) {
		ManagerMember managerMember = new ManagerMember();
		managerMember.setEmail(managerEmailChangeInternalDto.getEmail());
		managerMember.setId(managerEmailChangeInternalDto.getId());
		int updateRow =  managerMemberRepository.updateEmailById(managerMember);
		return updateRow == 1;
	}
	
	// 메니저 비밀번호 변경
	@Transactional
	public ChangeCode changePassword(PasswordChangeInternalDto passwordChangeInternalDto) {
		boolean nowPasswordCheck = passwordCheck(PasswordCheckInternalDto.builder()
				.managerMemberId(passwordChangeInternalDto.getManagerId())
				.password(passwordChangeInternalDto.getNowPassword())
				.build());
		if(nowPasswordCheck) {
			String newPasswordHashed = BcryptHashingEncoder.encode(passwordChangeInternalDto.getNewPassword());
			ManagerMember managerMember = new ManagerMember();
			managerMember.setId(passwordChangeInternalDto.getManagerId());
			managerMember.setPassword(newPasswordHashed);
			int updateRow = managerMemberRepository.updatePasswordById(managerMember);
			if(updateRow == 1) {
				return ChangeCode.SUCCESS;
			}else {
				return ChangeCode.PASSWORD_CHANGE_ERROR;
			}
		}else {
			return ChangeCode.NOW_PASSWORD_MISMATCH;
		}
	}
	
	// ===============================
	
	// 고객센터 전화번호 변경
	@Transactional
	public ChangeCode changeCustomerCenterPhoneNumber(CustomerCenterPhoneNumberChangeInternalDto customerCenterPhoneNumberChangeInternalDto){
		long shopInfoId = selectShopInfoIdByShopInfoId(customerCenterPhoneNumberChangeInternalDto.getManagerMemberId());
		return shopInfoService.changeShopPhoneNumber(ShopInfoPhoneNumberChangeInternalDto.builder()
				.phoneNumber(customerCenterPhoneNumberChangeInternalDto.getPhoneNumber())
				.shopInfoId(shopInfoId)
				.build());
	}
	
	// 메니저 id로 해당 메니저의 shopInfo 정보 가져오기
	public long selectShopInfoIdByShopInfoId(long managerMemberId) {
		return managerMemberRepository.selectShopInfoIdById(managerMemberId);
	}
	
	// 고객센터 주소 변경
	@Transactional
	public ChangeCode changeCustomerCenterAddress(CustomerChangeAddressInternalDto customerChangeAddressInternalDto) {
		long shopInfoId = selectShopInfoIdByShopInfoId(customerChangeAddressInternalDto.getManagerMemberId());
		return shopInfoService.changeShopAddress(ShopInfoAddressChangeInternalDto.builder()
				.shopInfoId(shopInfoId)
				.postCode(customerChangeAddressInternalDto.getPostCode())
				.address(customerChangeAddressInternalDto.getAddress())
				.detailAddress(customerChangeAddressInternalDto.getDetailAddress())
				.extraAddress(customerChangeAddressInternalDto.getExtraAddress())
				.build());
	}
	
	
}
