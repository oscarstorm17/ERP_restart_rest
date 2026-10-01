package com.example.demo.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.EmailVerification;
import com.example.demo.model.SignupRequest;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtpRequest;
import com.example.demo.repository.EmailVerificationRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SignupService {

	private final UserRepository userRepository;
	private final EmailVerificationRepository emailVerificationRepository;
	private final OtpService otpService;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	
	public SignupService(
			UserRepository userRepository,
            EmailVerificationRepository emailVerificationRepository,
            OtpService otpService,
            EmailService emailService,
            PasswordEncoder passwordEncoder
			) {
		this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.otpService = otpService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
	}

	public void startSignup(SignupRequest request) {
        String email = request.email().trim().toLowerCase();
        //System.out.println("Signup Service : start Signup : started");
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String otp = otpService.generateOtp();

        emailVerificationRepository.deleteByEmail(email);

        EmailVerification verification = new EmailVerification();
        verification.setEmail(email);
        verification.setOtpHash(otpService.hash(otp));
        verification.setExpiresAt(Instant.now().plusSeconds(300)); // 5 minutes
        verification.setAttempts(0);
        verification.setVerified(false);

        emailVerificationRepository.save(verification);

        // In production, do not log the OTP.
        emailService.sendOtp(email, otp);
    }
	
	
	@Transactional
	public void verifyOtp(VerifyOtpRequest request, String encodedPendingSignupPassword) {
	    String email = request.email().trim().toLowerCase();

	    EmailVerification verification =
	            emailVerificationRepository
	                    .findTopByEmailAndVerifiedFalseOrderByIdDesc(email)
	                    .orElseThrow(() ->
	                            new IllegalArgumentException("OTP not found"));

	    if (verification.getExpiresAt().isBefore(Instant.now())) {
	        throw new IllegalArgumentException("OTP has expired");
	    }

	    if (verification.getAttempts() >= 5) {
	        throw new IllegalArgumentException("Too many invalid attempts");
	    }

	    verification.setAttempts(verification.getAttempts() + 1);

	    if (!otpService.matches(request.otp(), verification.getOtpHash())) {
	        emailVerificationRepository.save(verification);
	        throw new IllegalArgumentException("Invalid OTP");
	    }

	    verification.setVerified(true);
	    emailVerificationRepository.save(verification);

//	    User user = new User();
//	    user.setEmail(email);
//	    user.setPassword(encodedPendingSignupPassword);  
//	    user.setEmailVerified(true);
//
//	    userRepository.save(user);
	}
}
