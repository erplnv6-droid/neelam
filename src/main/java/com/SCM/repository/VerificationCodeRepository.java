package com.SCM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{

	Optional<VerificationCode> findByEmail(String email);
	boolean existsByEmail(String email);
}
