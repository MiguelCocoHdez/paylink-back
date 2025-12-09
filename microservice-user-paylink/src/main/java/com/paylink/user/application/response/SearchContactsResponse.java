package com.paylink.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Builder
public class SearchContactsResponse {

	private Long userId;
	private String username;
	private String email;
	private String fullName;
}
