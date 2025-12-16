package com.paylink.user.infrastructure.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface SpringJpaContactRepository extends JpaRepository<ContactEntity, Long> {

	List<ContactEntity> findByUserId(Long userId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM contacts WHERE user_id = :userId AND contact_user_id = :contactUserId", nativeQuery = true)
	void deleteContactByUser(@Param("userId") Long userId, @Param("contactUserId") Long contactUserId);
}
