package com.paylink.user.infrastructure.persistance;

import org.springframework.stereotype.Repository;

import com.paylink.user.application.port.out.UserProfileRepository;
import com.paylink.user.domain.model.UserProfile;
import com.paylink.user.infrastructure.mapper.UserProfileMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserProfileRepository implements UserProfileRepository {
	
	private final SpringJpaUserProfileRepository upr;
	private final UserProfileMapper mapper;

	@Override
	public void save(UserProfile userProfile) {
		upr.save(mapper.toEntity(userProfile));
	}

	@Override
	public UserProfile findById(Long id) {
		UserProfileEntity user = upr.findUserById(id);
		UserProfile userIncomplete = null;
		
		if(user != null) {
			userIncomplete = mapper.toDomain(user);
		}
		
		return userIncomplete;
	}

	@Override
	public void completeUserProfile(UserProfile completedUser) {
		upr.completeUserProfile(completedUser.getId(), completedUser.getFullName(), completedUser.getCurrency());
	}

}
