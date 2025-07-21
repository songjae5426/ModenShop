package com.songjae.modenshop.member.manager.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder=true)
@Getter
@AllArgsConstructor
public class ManagerProfileInfoDto {
	private String managerName;
	private String managerProfileImg;
}
