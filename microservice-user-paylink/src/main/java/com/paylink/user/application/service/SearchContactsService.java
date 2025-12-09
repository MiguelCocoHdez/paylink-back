package com.paylink.user.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paylink.user.application.port.in.SearchContactsUseCase;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.application.response.SearchContactsResponse;
import com.paylink.user.domain.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchContactsService implements SearchContactsUseCase {
	
	private final UserProfileRepository upr;
	
	@Override
	public List<SearchContactsResponse> searchContacts(String contactInfo) {
		List<UserProfile> searchedUsers = upr.searchUsers(contactInfo);
		
		return searchedUsers.stream()
				.map(user -> SearchContactsResponse.builder()
						.userId(user.getId())
						.username(user.getUsername())
						.email(user.getEmail())
						.fullName(user.getFullName())
						.build())
				.toList();
	}

}
