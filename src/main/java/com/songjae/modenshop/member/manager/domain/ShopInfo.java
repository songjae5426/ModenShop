package com.songjae.modenshop.member.manager.domain;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopInfo {
	private long id;
	private String shopPhoneNumber;
	private String shopPostCode;
	private String shopAddress;
	private String shopDetailAddress;
	private String shopExtraAddress = null;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
