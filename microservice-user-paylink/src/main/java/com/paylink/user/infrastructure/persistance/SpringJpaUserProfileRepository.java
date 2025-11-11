package com.paylink.user.infrastructure.persistance;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface SpringJpaUserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

	UserProfileEntity findUserById(Long id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_profiles SET full_name = :fullName, currency = :currency WHERE id = :id", nativeQuery = true)
	void completeUserProfile(@Param("id") Long id, @Param("fullName") String fullName, @Param("currency") String currency);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_profiles SET balance = :balance WHERE id = :id", nativeQuery = true)
	void setBalance(@Param("balance") BigDecimal balance, @Param("id") Long id);
}
