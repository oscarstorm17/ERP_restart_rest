package com.example.demo.controller;

import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SignupRequest;
import com.example.demo.model.VerifyOtpRequest;
import com.example.demo.service.SignupService;

@RestController
@RequestMapping("/api/auth")
public class SignupController {

    private final SignupService signupService;
    private final PasswordEncoder passwordEncoder;
    public SignupController(SignupService signupService, 
    		PasswordEncoder passwordEncoder) {
        this.signupService = signupService;
        this.passwordEncoder = passwordEncoder;
    }
    String encodedPendingSignupPassword;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignupRequest request) {
        signupService.startSignup(request);
        encodedPendingSignupPassword = passwordEncoder.encode(request.password());
        return ResponseEntity.ok(
                java.util.Map.of("message", "OTP sent to your email")
        );
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@Validated @RequestBody VerifyOtpRequest request) {
        signupService.verifyOtp(request, encodedPendingSignupPassword);

        return ResponseEntity.ok(
                java.util.Map.of("message", "Email verified and account created \n Redirecting to login")
        );
    }
}

