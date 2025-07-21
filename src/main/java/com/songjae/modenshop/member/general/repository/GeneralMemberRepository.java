package com.songjae.modenshop.member.general.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.songjae.modenshop.member.general.domain.GeneralMember;

@Mapper
public interface GeneralMemberRepository {
	
	// 이메일을 입력 받아 해당 이메일과 같은 이메일이 있는 행의 여부 반환
	public boolean existsByEmail(@Param("email") String email);
	
	// 닉네임
	public boolean existsByNickName(@Param("nickName") String nickName);
	
	// 핸드폰번호
	public boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
	// 입력받은 이메일로 비밀번호를 가져온다
	public GeneralMember selectIdAndPasswordByEmail(@Param("email") String email);
	
	// 회원가입
	public int insertGeneralMemberInfo(GeneralMember generalMember);
	
	// memberID를 입력받아 해당 유저의 프로필 사진 닉네임 가져오기
	
	// 닉네임 프로필 사진 만 조회
	// 두개 이상일떄는 DTO로 반환 new com.songjae.modenshop.member.general.dto.UserReviewSummaryDto(조회할 엔터티클래스 필드명) dto 생성자
//	@Query("SELECT new com.songjae.modenshop.member.general.dto.GeneralMemberReviewCardItemDto(id, nickName, profileImg) FROM GeneralMember WHERE id = :id")
//	public GeneralMemberReviewCardItemDto findGeneralMemberReviewCardItemDtoById(@Param("id") long id);
}
