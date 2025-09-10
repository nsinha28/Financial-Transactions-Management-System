package com.example.ftms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ftms.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByUsername(String userName);
}
