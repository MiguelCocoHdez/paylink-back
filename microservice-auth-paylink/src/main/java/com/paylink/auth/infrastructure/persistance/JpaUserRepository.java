package com.paylink.auth.infrastructure.persistance;

import org.springframework.stereotype.Repository;

import com.paylink.auth.application.port.out.UserRepository;
import com.paylink.auth.domain.model.User;
import com.paylink.auth.infrastructure.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

	private final SpringJpaUserRepository ur;
	private final UserMapper mapper;

	@Override
	public User save(User user) {
		UserEntity userSaved = ur.save(mapper.toEntity(user));
		
		return mapper.toDomain(userSaved);
	}

	@Override
	public boolean existsByEmail(String email) {
		return ur.existsByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username) {
		return ur.existsByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		UserEntity entity = ur.findByEmail(email);
		User user = null;
		
		if(entity != null) {
			user = mapper.toDomain(entity);
		}
		
		return user;
	}

	@Override
	public User findByUsername(String username) {
		UserEntity entity = ur.findByUsername(username);
		User user = null;
		
		if(entity != null) {
			user = mapper.toDomain(entity);
		}
		
		return user;
	}
}