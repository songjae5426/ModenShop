package com.songjae.modenshop.member.manager.domain;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerMember {
	private long id;
	private long shopInfoId = 1;
	private String email;
	private String password;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
