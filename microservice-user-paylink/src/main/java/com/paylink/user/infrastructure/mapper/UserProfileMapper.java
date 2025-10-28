package com.paylink.user.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.paylink.user.domain.model.UserProfile;
import com.paylink.user.infrastructure.persistance.UserProfileEntity;

@Component
public class UserProfileMapper {

	public UserProfile toDomain(UserProfileEntity entity) {
		return UserProfile.fromEntity(entity.getId(),
				entity.getAuthUserId(),
				entity.getUsername(),
				entity.getEmail(), 
				entity.getFullName(), 
				entity.getBalance(),
				entity.getCurrency());
	}
	
	public UserProfileEntity toEntity(UserProfile domain) {
		UserProfileEntity entity = new UserProfileEntity();
        
        entity.setId(domain.getId());
        entity.setAuthUserId(domain.getAuthUserId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setFullName(domain.getFullName());
        entity.setBalance(domain.getBalance());
        entity.setCurrency(domain.getCurrency());

        return entity;
	}
	
}
