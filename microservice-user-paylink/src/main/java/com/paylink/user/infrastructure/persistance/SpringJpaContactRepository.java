package com.paylink.user.infrastructure.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaContactRepository extends JpaRepository<ContactEntity, Long> {

	List<ContactEntity> findByUserId(Long userId);
}
