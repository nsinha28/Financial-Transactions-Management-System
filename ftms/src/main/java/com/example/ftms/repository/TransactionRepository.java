package com.example.ftms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ftms.model.AccountEntity;
import com.example.ftms.model.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
	
	//find all transactions by account
	List<TransactionEntity> findByAccount(AccountEntity account);
	
	// Find by account + type
	List<TransactionEntity> findByAccountAndType(AccountEntity account, String type);

	// Find by account + date range
	List<TransactionEntity> findByAccountAndTimestampBetween(AccountEntity account, LocalDateTime start, LocalDateTime end);
}
