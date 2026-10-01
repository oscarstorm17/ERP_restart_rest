package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final PasswordEncoder passwordEncoder;

    public OtpService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String generateOtp() {
        return String.format("%06d", secureRandom.nextInt(1_000_000));
    }

    public String hash(String otp) {
        return passwordEncoder.encode(otp);
    }

    public boolean matches(String otp, String hash) {
        return passwordEncoder.matches(otp, hash);
    }
}

