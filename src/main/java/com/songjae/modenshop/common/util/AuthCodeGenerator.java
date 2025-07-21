package com.songjae.modenshop.common.util;

import java.security.SecureRandom;

public class AuthCodeGenerator {
	// 이메일 인증 코드 만드는 메소드
	public static String authCodeGenerator(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		SecureRandom random = new SecureRandom(); // SecureRandom클래스는 Random클래스 처럼 난수를 생성하지만 더 보안이 강화된 난수 생성기이다 => 비교적
													// 느리지만 더 랜덤함
		StringBuilder code = new StringBuilder(); // String은 수정될때마다 계속 객체가 생성, StringBuilder문자열을 이어 붙이기 때문에(계속 수정) 되어도
													// 하나의 객체 => 메모리 관리 효율적
		// 반복
		for (int i = 0; i < length; i++) {
			// nextInt(10) => 0부터 9까지의 랜덤 수 발생
			// characters에서 발생한 난수를 인덱스로 문자를 가져와 StringBuilder의 문자열에 이어 붙인다
			code.append(characters.charAt(random.nextInt(characters.length())));
		}
		return code.toString(); // StringBuilder를 문자열로 반환
	}
}
