package com.example.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	//since now all of our function throws Exception(s) when things go wrong, we need to have Global Exception Handler.
	
	private final UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	
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
	
	//above is written short form of the code.
	//same can be written in below format
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
	
	public User createUser(User user_) {
		if(user_.getEmail() == null || user_.getEmail().isBlank() ||
		   user_.getUsername() == null || user_.getUsername().isBlank() ||
		   user_.getPassword() == null || user_.getPassword().isBlank()){
			throw new IllegalFieldException();
		}
		user_.setUsername(user_.getUsername().trim());
		user_.setEmail(user_.getEmail().trim());
		return userRepository.save(user_);  //always returns an object. save cannot return null
		
	}
}
