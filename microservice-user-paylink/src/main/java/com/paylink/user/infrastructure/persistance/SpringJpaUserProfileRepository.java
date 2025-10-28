package com.paylink.user.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaUserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

}
