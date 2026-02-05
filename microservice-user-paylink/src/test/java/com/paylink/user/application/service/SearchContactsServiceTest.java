package com.paylink.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.application.response.SearchContactsResponse;
import com.paylink.user.domain.model.UserProfile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test SearchContacts Service")
public class SearchContactsServiceTest {

	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private SearchContactsService searchContactsService;
	
	private UserProfile user1;
	private UserProfile user2;
	
	@BeforeEach
	void setUp() {
		user1 = UserProfile.fromEntity(
				1L,
				10L,
				"user1",
				"user1@gmail.com",
				"User One",
				new BigDecimal("100.00"),
				"EUR"
		);
		
		user2 = UserProfile.fromEntity(
				2L,
				20L,
				"user2",
				"user2@gmail.com",
				"User Two",
				new BigDecimal("200.00"),
				"USD"
		);
	}
	
	@Test
	@DisplayName("Debe buscar los usuarios y mapearlos a SearchContactsResponse")
	void debeBuscarYMapearUsuarios() {
		when(upr.searchUsers("user")).thenReturn(List.of(user1, user2));
		
		List<SearchContactsResponse> response = searchContactsService.searchContacts("user");
		
		verify(upr).searchUsers("user");
		
		assertEquals(2, response.size());
		
		assertEquals(1L, response.get(0).getUserId());
		assertEquals("user1", response.get(0).getUsername());
		assertEquals("user1@gmail.com", response.get(0).getEmail());
		assertEquals("User One", response.get(0).getFullName());
		
		assertEquals(2L, response.get(1).getUserId());
		assertEquals("user2", response.get(1).getUsername());
		assertEquals("user2@gmail.com", response.get(1).getEmail());
		assertEquals("User Two", response.get(1).getFullName());
	}
}
