package com.paylink.user.application.port.out;

import java.math.BigDecimal;

import com.paylink.user.domain.model.UserProfile;

public interface UserProfileRepository {

	void save(UserProfile userProfile);
	
	UserProfile findById(Long id);
	
	void completeUserProfile(UserProfile completedUser);
	
	void setBalance(BigDecimal balance, Long id);
}
