package com.songjae.modenshop.member.manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.songjae.modenshop.member.general.GeneralMemberController;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto;
import com.songjae.modenshop.member.general.dto.internal.LoginCheckInternalDto.LoginCheckState;
import com.songjae.modenshop.member.general.dto.request.LoginRequestDto;
import com.songjae.modenshop.member.general.dto.response.LoginCheckResponseDto;
import com.songjae.modenshop.member.manager.dto.internal.CustomerCenterPhoneNumberChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.CustomerChangeAddressInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.ManagerEmailChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.PasswordChangeInternalDto;
import com.songjae.modenshop.member.manager.dto.internal.PasswordCheckInternalDto;
import com.songjae.modenshop.member.manager.dto.request.CustomerCenterPhoneNumberChangeRequestDto;
import com.songjae.modenshop.member.manager.dto.request.CustomerChangeAddressRequestDto;
import com.songjae.modenshop.member.manager.dto.request.ManagerEmailChangeRequestDto;
import com.songjae.modenshop.member.manager.dto.request.ManagerIdentityVerificationReqeustDto;
import com.songjae.modenshop.member.manager.dto.request.ManagerPasswordChangeRequestDto;
import com.songjae.modenshop.member.manager.dto.response.ManagerIdentityVerificationResponseDto;
import com.songjae.modenshop.member.manager.dto.response.ManagerInfoChangeResponseDto;
import com.songjae.modenshop.member.manager.dto.response.ManagerInfoChangeResponseDto.ChangeCode;
import com.songjae.modenshop.member.manager.dto.response.ManagerProfileResponseDto;
import com.songjae.modenshop.member.manager.service.ManagerMemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/moden-shop")
@RestController
public class ManagerMemberRestController {

	private final ManagerMemberService managerMemberService;


	// 관리자 회원 로그인
	@PostMapping("/manager/member/login")
	public LoginCheckResponseDto managerMemberLogin(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
		LoginCheckInternalDto loginCheckInternalDto = managerMemberService.managerMemberLoginCheck(loginRequestDto.getEmail(), loginRequestDto.getPassword());
		if(loginCheckInternalDto.getLoginCheckState() == LoginCheckState.SUCCESS) {
			session.setAttribute("memberType", "manager");
			session.setAttribute("memberId", loginCheckInternalDto.getId());
		}
		return LoginCheckResponseDto.builder()
				.loginCheckState(loginCheckInternalDto.getLoginCheckState())
				.build();
	}

	// 관리자페이지 관리자 정보 수정 본인인증
	@PostMapping("/manager/identity/verification/session/check")
	public ManagerIdentityVerificationResponseDto managerPageIdentityVerification(
			@RequestBody ManagerIdentityVerificationReqeustDto ManagerIdentityVerificationReqeustDto,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		long managerMemberId = (long) session.getAttribute("memberId");
		boolean passwordCheck = managerMemberService.passwordCheck(PasswordCheckInternalDto.builder()
				.managerMemberId(managerMemberId)
				.password(ManagerIdentityVerificationReqeustDto.getPassword())
				.build());
		return ManagerIdentityVerificationResponseDto.builder()
				.success(passwordCheck)
				.build();
	}

	// 관리자 페이지 로그인시 관리자 이름 전달 하기
	@GetMapping("/manager/profile/session/check")
	public ManagerProfileResponseDto getManagerProfileInfoDto(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		long managerMemberId = (long) session.getAttribute("memberId");
		String managerName = managerMemberService.getManagerName(managerMemberId);
		return ManagerProfileResponseDto.builder()
				.name(managerName)
				.build();
	}

	// 이메일 변경
	@PostMapping("/manager/member/email/change/session/check")
	public ManagerInfoChangeResponseDto changeEmail(@RequestBody ManagerEmailChangeRequestDto managerEmailChangeRequestDto, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		long managerMemberId = (long) session.getAttribute("memberId");
		boolean emailChangeSuccess = managerMemberService.changeEmail(
				ManagerEmailChangeInternalDto
				.builder()
				.email(managerEmailChangeRequestDto.getEmail())
				.id(managerMemberId)
				.build());
		if(emailChangeSuccess) {
			return ManagerInfoChangeResponseDto.builder()
					.success(emailChangeSuccess)
					.build();
		}else {
			return ManagerInfoChangeResponseDto.builder()
					.success(emailChangeSuccess)
					.changeCode(ChangeCode.EMAIL_CHANGE_ERROR)
					.build();
		}
	}

	// 비밀번호 변경
	@PostMapping("/manager/member/password/change/session/check")
	public ManagerInfoChangeResponseDto changePassword(
			@RequestBody ManagerPasswordChangeRequestDto managerPasswordChangeRequestDto,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		long managerMemberId = (long) session.getAttribute("memberId");
		ChangeCode changeCode = managerMemberService.changePassword(
				PasswordChangeInternalDto.builder()
				.managerId(managerMemberId)
				.newPassword(managerPasswordChangeRequestDto.getNewPassword())
				.nowPassword(managerPasswordChangeRequestDto.getNowPassword())
				.build());
		if(changeCode == ChangeCode.SUCCESS) {
			return ManagerInfoChangeResponseDto.builder()
			.success(true)
			.changeCode(changeCode)
			.build();
		}
		return ManagerInfoChangeResponseDto.builder()
				.success(false)
				.changeCode(changeCode)
				.build();
	}

	// ----------------------
	// 고객센터 전화번호 변경
	@PostMapping("manager/customer/center/Phone/number/change/session/check")
	public ManagerInfoChangeResponseDto changeCustomerCenterPhoneNumber(
			HttpServletRequest request,
			@RequestBody CustomerCenterPhoneNumberChangeRequestDto customerCenterPhoneNumberChangeRequestDto) {
		HttpSession session = request.getSession();
		long managerMemberId = (long) session.getAttribute("memberId");
		ChangeCode changeCode = managerMemberService.changeCustomerCenterPhoneNumber(
				CustomerCenterPhoneNumberChangeInternalDto.builder()
				.managerMemberId(managerMemberId)
				.phoneNumber(customerCenterPhoneNumberChangeRequestDto.getPhoneNumber())
				.build());
		if (changeCode == ChangeCode.SUCCESS) {
			return ManagerInfoChangeResponseDto.builder()
					.success(true)
					.changeCode(changeCode)
					.build();
		} else {
			return ManagerInfoChangeResponseDto.builder()
					.success(false)
					.changeCode(changeCode)
					.build();
		}
	}
	
	
	
//
//	// 고객센터 주소 변경
	@PostMapping("manager/customer/center/address/change/session/check")
	public ManagerInfoChangeResponseDto changeCustomerCenterAddress(HttpServletRequest request,
			@RequestBody CustomerChangeAddressRequestDto customerChangeAddressRequestDto) {
		HttpSession session = request.getSession();
		long managerMemberId = (long) session.getAttribute("memberId");
		ChangeCode changeCode = managerMemberService.changeCustomerCenterAddress(CustomerChangeAddressInternalDto.builder()
				.managerMemberId(managerMemberId)
				.postCode(customerChangeAddressRequestDto.getPostCode())
				.address(customerChangeAddressRequestDto.getAddress())
				.detailAddress(customerChangeAddressRequestDto.getDetailAddress())
				.extraAddress(customerChangeAddressRequestDto.getExtraAddress())
				.build());
		if (changeCode == ChangeCode.SUCCESS) {
			return ManagerInfoChangeResponseDto.builder()
					.success(true)
					.changeCode(changeCode)
					.build();
		} else {
			return ManagerInfoChangeResponseDto.builder()
					.success(false)
					.changeCode(changeCode)
					.build();
		}
	}

}
