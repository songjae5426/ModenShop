package com.songjae.modenshop.member.manager.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.songjae.modenshop.member.manager.domain.ManagerMember;
import com.songjae.modenshop.member.manager.dto.ManagerProfileInfoDto;

@Mapper
public interface ManagerMemberRepository {
	public ManagerProfileInfoDto selectManagerProfileInfo(@Param("managerMemberId") Long managerMemberId);

	// email이 같은 행 갯수 조회
		public boolean existsByEmail(@Param("email") String email);
		
		// 입력받은 이메일을 통해 해당 행의 모든 칼럼 값을 가져온다
		public ManagerMember selectIdAndPasswordByEmail(@Param("email") String email);

		// 관리자 이름 조회
		//JPQL : 엔터티 중심의 언어 => 테이블명, 칼럼명 모두 DB에 만들어진 그대로가 아닌 엔터티클래스에 명시한데로 사용
		public String selectNameById(@Param("id") long id);
		
		// 메니저id를 입력받아 해당 행의 passwordHash값 가져오기
		public String selectPasswordById(@Param("id") long id);
		
		// 메니저 id를 입력받아 해당 행 이메일 수정
		public int updateEmailById(ManagerMember managerMember);
		
		// 메니저 id를 입력받아 해당 행 비밀번호 수정
		public int updatePasswordById(ManagerMember managerMember);
		
		// 메니저id를 입력받아 shopInfoId 조회
		public long selectShopInfoIdById(@Param("id") long id);
		
//		// 메니저id를 입력받아 해당 메니저의 shopInfoId가져오기
//		@Query("SELECT shopInfoId FROM ManagerMember WHERE id = :managerMemberId")
//		public long findShopInfoIdByManagerMemberId(@Param("managerMemberId") long managerMemberId);
}
