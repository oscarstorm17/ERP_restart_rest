package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.EmailVerification;

public interface EmailVerificationRepository
extends JpaRepository<EmailVerification, Long> {

Optional<EmailVerification> findTopByEmailAndVerifiedFalseOrderByIdDesc(
    String email
);

void deleteByEmail(String email);
}
