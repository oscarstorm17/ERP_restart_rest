package com.example.demo.model;

public record VerifyOtpRequest(
        String email,
        String otp
) {}
