package com.paylink.transation.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import jakarta.transaction.Transactional;

public interface SpringJpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE transactions SET status = :status WHERE id = :id", nativeQuery = true)
	void changeStatus(@Param("id") Long id, @Param("status") String status);
	
}
