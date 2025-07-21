package com.songjae.modenshop.member.general.domain;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralMember {
	private long id;
	private String email;
	private String password;
	private String name;
	private String nickName;
	private String profileImg = "/img/basicProfileImg.png";
	private String phoneNumber;
	private String postCode;
	private String address;
	private String detailAddress;
	private String extraAddress;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
