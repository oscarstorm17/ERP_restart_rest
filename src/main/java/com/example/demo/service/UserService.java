package com.example.demo.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.PendingSignup;
import com.example.demo.model.User;
import com.example.demo.repository.PendingSignupRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	//since now all of our function throws Exception(s) when things go wrong, we need to have Global Exception Handler.
	
	private final UserRepository userRepository;
	private final OtpService otpService;
	private final PasswordEncoder passwordEncoder;
	private final PendingSignupRepository pendingSignupRepository;
	
	public UserService(UserRepository userRepository,
			OtpService otpService,
			PasswordEncoder passwordEncoder,
			PendingSignupRepository pendingSignupRepository
			) {
		this.userRepository=userRepository;
		this.otpService = otpService;
		this.passwordEncoder = passwordEncoder;
		this.pendingSignupRepository = pendingSignupRepository;
	}
	
	public User getUserById(Long id) {
	    return userRepository.findById(id) // automatically converts Optional<User> to User
	            .orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public User getUserByUsername(String username_) {
		//Optional<User> user = userRepository.findByUsername(username_);
		return userRepository.findByUsername(username_)
				.orElseThrow(()-> new UserNotFoundException(username_)
				);
	}
	
	
	//check if username matches the REGEX
	//check if username is already present or not
	//returns {"available": false} OR {"available": true}
	public ResponseEntity<?> checkUsername(String username_) {
		String username = username_.trim();
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*_)[a-zA-Z0-9_]{4,20}$";
		if (!username.matches(regex)) {
			System.out.println("Illegal Username");
            return ResponseEntity.badRequest().body(
                    Map.of("message",
                            "Username must be 3–20 characters and contain only letters, numbers, or underscores")
            );
        }
		else {
			boolean exists = userRepository.findByUsername(username).isPresent();
			if(exists) {
				System.out.println("Username un-available "+username);
				return ResponseEntity.ok(
		                Map.of("available", false));
			}
			else {
				System.out.println("Username available "+username);
				return ResponseEntity.ok(
		                Map.of("available", true)
		        );
			}
		}	
	}
	
	
	public User getUserByEmail(String email_) {
		Optional<User> user = userRepository.findByEmail(email_);
		if(user.isPresent()) {
			//System.out.println("User Service: User found");
			return user.get();
		}
		else {
			//System.out.println("User Service: User not found");
			throw new UserNotFoundException(email_) ;
		}
	}

	
	//create user
	//also generate the OTP - still implementing
	public ResponseEntity<?> createUser(User user_) {
		if(user_.getEmail() == null || user_.getEmail().isBlank() ||
		   user_.getUsername() == null || user_.getUsername().isBlank() ||
		   user_.getPassword() == null || user_.getPassword().isBlank()){
			throw new IllegalFieldException();
		}
		if( userRepository.findByEmail(user_.getEmail()).isPresent() ) {
			//user already registered
			return ResponseEntity.ok(
	                Map.of("registered", false)
	        );
		}
		System.out.println("user present?   :"+ userRepository.findByEmail(user_.getEmail()).isPresent() );
		user_.setUsername(user_.getUsername().trim());
		user_.setEmail(user_.getEmail().trim().toLowerCase());
		user_.setPassword(passwordEncoder.encode(user_.getPassword()));
		//userRepository.save(user_);  //always returns an object. save cannot return null
		//generate OTP
		String otp = otpService.generateOtp();
		otpService.sendOtp(user_.getEmail(), otp);
		// store all the data in PendingSignup object
		String otpHash = otpService.hash(otp);
		//now validate the otp when user receives, enters and press submit button
		pendingSignupRepository.deleteByEmail(user_.getEmail());
		PendingSignup pendingSignup = new PendingSignup();
		pendingSignup.setEmail(user_.getEmail());
		pendingSignup.setUsername(user_.getUsername());
		pendingSignup.setPasswordHash(user_.getPassword());
		pendingSignup.setOtpHash(otpHash);
		pendingSignup.setExpiresAt(Instant.now().plusSeconds(300)); // 5 mins
		pendingSignup.setAttempts(0);
		pendingSignup.setVerified(false);
		pendingSignupRepository.save(pendingSignup);
		
		return ResponseEntity.ok(
                Map.of("registered", "pending")
        );
	}
}
