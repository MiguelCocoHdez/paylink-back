package com.paylink.auth.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.paylink.auth.domain.model.User;
import com.paylink.auth.infrastructure.persistance.UserEntity;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return User.fromEntity(entity.getId(), 
        		entity.getUsername(), 
        		entity.getEmail(), 
        		entity.getPassword(), 
        		entity.getRole(), 
        		entity.getAuthProvider());
    }

    public UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setRole(domain.getRole());
        entity.setAuthProvider(domain.getAuthProvider());

        return entity;
    }
}
