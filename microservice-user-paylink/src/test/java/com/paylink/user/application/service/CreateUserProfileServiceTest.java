package com.paylink.user.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paylink.kafka.events.UserRegisteredEvent;
import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test CreateUserProfile Service")
public class CreateUserProfileServiceTest {

	@Mock
	private UserProfileRepository upr;
	
	@InjectMocks
	private CreateUserProfileService createUserProfileService;
	
	private UserRegisteredEvent event;
	
	@BeforeEach
	void setUp() {
		event = new UserRegisteredEvent(
				10L,
				"testuser",
				"test@gmail.com"
		);
	}
	
	@Test
	@DisplayName("Debe crear y guardar el perfil de usuario")
	void debeCrearYGuardarUserProfile() {
		createUserProfileService.createUserProfile(event);
		
		verify(upr).save(any(UserProfile.class));
	}
}
