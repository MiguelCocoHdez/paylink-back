package com.paylink.user.application.port.out;

import com.paylink.user.domain.model.UserProfile;

public interface UserProfileRepository {

	void save(UserProfile userProfile);
	
	UserProfile findById(Long id);
	
	void completeUserProfile(UserProfile completedUser);
}
