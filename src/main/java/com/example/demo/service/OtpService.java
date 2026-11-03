package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.PendingSignup;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtpRequest;
import com.example.demo.repository.EmailVerificationRepository;
import com.example.demo.repository.PendingSignupRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;

@Service
public class OtpService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final PendingSignupRepository pendingSignupRepository;
    private final UserRepository userRepository;
    
    public OtpService(PasswordEncoder passwordEncoder,
    		JavaMailSender mailSender,
    		PendingSignupRepository pendingSignupRepository,
    		UserRepository userRepository
    		) {
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.pendingSignupRepository = pendingSignupRepository;
        this.userRepository = userRepository;
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
    
    public void sendOtp(String recipient, String otp) {
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject("SplitFree Email Verification Code");
        message.setText(
                "Your verification code is: " + otp +
                "\n\nThis code expires in 5 minutes."
        );
        mailSender.send(message);
    }
    
    //verify otp
    //input: request.email     request.otp
	@Transactional
	public ResponseEntity<?> verifyOtp(VerifyOtpRequest request) {
	    String email = request.email().trim().toLowerCase();
	    String otp = request.otp().trim();
	    PendingSignup pending = pendingSignupRepository.findByEmail(email)
	    		.orElseThrow(() -> new IllegalArgumentException("OTP Not Found, generate a new one")) ;
	    if(pending.getExpiresAt().isBefore(Instant.now())) {
	    	//OTP Expired
	    	throw new IllegalArgumentException("OTP has Expired");
	    }
	    if (pending.getAttempts() >= 5) {
	        throw new IllegalArgumentException("Too many invalid attempts");
	    }
	    pending.setAttempts(pending.getAttempts() + 1);
	    if(!matches(otp, pending.getOtpHash())) {
	    	//otp incorrect
	    	pendingSignupRepository.save(pending);
	    	throw new IllegalArgumentException("Invalid OTP");
	    }
	    //OTP is correct
	    pending.setVerified(true);
	    pendingSignupRepository.save(pending);
	    User user = new User();
	    user.setEmail(email);
	    user.setPassword(pending.getPasswordHash());  
	    user.setUsername(pending.getUsername());
	    userRepository.save(user);
	    return ResponseEntity.ok(
	    		Map.of("registered", true )
	    		);
	    
	    
	    
	    
	    
	}
}

