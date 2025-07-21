package com.songjae.modenshop.member.general.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.songjae.modenshop.common.util.BcryptHashingEncoder;
import com.songjae.modenshop.member.general.domain.GeneralMember;
import com.songjae.modenshop.member.general.dto.JoinDuplicationCheckInternalDto;
import com.songjae.modenshop.member.general.dto.JoinDuplicationCheckInternalDto.DuplicationName;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto.LoginCheckState;
import com.songjae.modenshop.member.general.dto.request.JoinRequestDto;
import com.songjae.modenshop.member.general.dto.response.JoinResponse;
import com.songjae.modenshop.member.general.dto.response.JoinResponse.JoinFailCause;
import com.songjae.modenshop.member.general.repository.GeneralMemberRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/moden-shop")
@RequiredArgsConstructor
@Service
public class GeneralMemberService {
	private final GeneralMemberRepository generalMemberRepository;

	// 회원 가입
	@Transactional
	public JoinResponse generalMemberJoin(JoinRequestDto joinRequestDto) {

	    // 1. 중복 검사
	    JoinDuplicationCheckInternalDto duplicationCheckResult = joinDuplicationCheck(
	            joinRequestDto.getEmail(),
	            joinRequestDto.getNickName(),
	            joinRequestDto.getPhoneNumber()
	    );

	    if (duplicationCheckResult.isDuplication()) {
	        return JoinResponse.builder()
	                .joinSuccess(false)
	                .joinFailCause(JoinFailCause.DUPLICATION)
	                .duplicationName(duplicationCheckResult.getDuplicationName())
	                .build();
	    }

	    // 2. 비밀번호 해싱
	    String hashedPassword = BcryptHashingEncoder.encode(joinRequestDto.getPassword());

	    // 3. 엔티티 생성
	    GeneralMember generalMember = new GeneralMember();
	    generalMember.setEmail(joinRequestDto.getEmail());
	    generalMember.setPassword(hashedPassword);
	    generalMember.setName(joinRequestDto.getName());
	    generalMember.setNickName(joinRequestDto.getNickName());
	    generalMember.setPhoneNumber(joinRequestDto.getPhoneNumber());
	    generalMember.setPostCode(joinRequestDto.getPostCode());
	    generalMember.setAddress(joinRequestDto.getAddress());
	    generalMember.setDetailAddress(joinRequestDto.getDetailAddress());
	    generalMember.setExtraAddress(joinRequestDto.getExtraAddress());


	    // 4. 저장
	    int createRow = generalMemberRepository.insertGeneralMemberInfo(generalMember);

	    // 5. 결과 리턴
	    if (createRow == 1) {
	        return JoinResponse.builder()
	                .joinSuccess(true)
	                .build();
	    } else {
	        return JoinResponse.builder()
	                .joinSuccess(false)
	                .joinFailCause(JoinFailCause.JOIN_ERROR)
	                .build();
	    }
	}


	// 회원가입 중복 검사
	public JoinDuplicationCheckInternalDto joinDuplicationCheck(
	        String email,
	        String nickName,
	        String phoneNumber) {

		DuplicationName duplicationName = null;

	    if (emailDuplicationCheck(email)) {
	        duplicationName = DuplicationName.EMAIL;
	    } else if (nickNameDuplicationCheck(nickName)) {
	        duplicationName = DuplicationName.NICK_NAME;
	    } else if (phoneNumberDuplicationCheck(phoneNumber)) {
	        duplicationName = DuplicationName.PHONE_NUMBER;
	    }

	    boolean isDuplicated = duplicationName != null;

	    return JoinDuplicationCheckInternalDto.builder()
	            .duplication(isDuplicated)
	            .duplicationName(duplicationName)
	            .build();
	}

	
	// 중복 확인 (중복이면 true)
	// 이메일 중복 확인
	public boolean emailDuplicationCheck(String email) {
		return generalMemberRepository.existsByEmail(email);
	}
	// 닉네임 중복 확인
	public boolean nickNameDuplicationCheck (String nickName) {
		return generalMemberRepository.existsByNickName(nickName);
	}
	// 전화번호 중복 확인
	public boolean phoneNumberDuplicationCheck (String phoneNumber) {
		return generalMemberRepository.existsByPhoneNumber(phoneNumber);
	}

	

	// 로그인 체크 
	public LoginCheckInternalDto generalMemberLoginCheck(String email, String password) {
		GeneralMember generalMember = generalMemberRepository.selectIdAndPasswordByEmail(email);	// 해당 행이 없으면 null 반환
		if (generalMember == null) {
	        return LoginCheckInternalDto.builder()
	        		.loginCheckState(LoginCheckState.EMAIL_NOT_FOUND)
	        		.build();
	    }
		boolean passwordMatches = BcryptHashingEncoder.hashCheck(password, generalMember.getPassword());
		if (!passwordMatches) {
			return LoginCheckInternalDto.builder()
	        		.loginCheckState(LoginCheckState.INVALID_PASSWORD)
	        		.build();
	    }
		return LoginCheckInternalDto.builder()
        		.loginCheckState(LoginCheckState.SUCCESS)
        		.id(generalMember.getId())
        		.build();
	}
	
	// memberId로 해당 계정의 프로필 사진 닉네임 가져오기
//	public GeneralMemberReviewCardItemDto getGeneralMemberReviewCardItemDtoById(long memberId) {
//		GeneralMemberReviewCardItemDto generalMemberReviewCardItemDto = generalMemberRepository.findGeneralMemberReviewCardItemDtoById(memberId);
//		return generalMemberReviewCardItemDto;
//	}
	
	
}
