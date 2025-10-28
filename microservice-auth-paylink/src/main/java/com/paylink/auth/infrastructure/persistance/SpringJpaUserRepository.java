package com.paylink.auth.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaUserRepository extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
	
	UserEntity findByEmail(String email);
	
	UserEntity findByUsername(String username);
}
