package com.example.ftms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ftms.model.AccountEntity;
import com.example.ftms.model.UserEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>{

	AccountEntity findByAccountNumber(String accountNo);
	List<AccountEntity> findByUser(UserEntity user);
}
