package com.songjae.modenshop.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptHashingEncoder {
	
	public static String encode(String str) {
		// Bcrypt 해싱 알고리즘은 내부적으로 Salt(솔트)가 적용되기 때문에 같은 값을 넣어도 다른 값이 나온다
		// 비교시
//		public boolean isPasswordMatch(String rawPassword, String storedHash) {
//		    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		    return encoder.matches(rawPassword, storedHash);
//		}
		BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
		String hashedStr = bcEncoder.encode(str);
		return hashedStr;
	}
	
	public static boolean hashCheck(String str, String hash) {
		BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
		return bcEncoder.matches(str, hash);
	}
}
