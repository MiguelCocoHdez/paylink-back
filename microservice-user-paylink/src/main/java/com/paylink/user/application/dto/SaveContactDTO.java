package com.paylink.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

public class SaveContactDTO {

	private Long userId;
	private Long contactUserId;
}
