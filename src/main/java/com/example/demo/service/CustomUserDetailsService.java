//package com.example.demo.service;
//
//import java.util.Optional;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//	//This class handles only login 
//	//signup is handled by post method in UserService
//
//	private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository=userRepository;
//    }
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return org.springframework.security.core.userdetails.User
//                .builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();	
//	}
//}
