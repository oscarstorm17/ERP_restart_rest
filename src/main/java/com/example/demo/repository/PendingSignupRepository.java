package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PendingSignup;

public interface PendingSignupRepository
extends JpaRepository<PendingSignup, Long> {

Optional<PendingSignup> findByEmail(String email);

void deleteByEmail(String email);
}

