package com.paylink.auth.application.port.out;

import com.paylink.auth.domain.model.User;

public interface UserRepository {

	User save(User user);
	
	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
	
	User findByEmail(String email);
	
	User findByUsername(String username);
}
