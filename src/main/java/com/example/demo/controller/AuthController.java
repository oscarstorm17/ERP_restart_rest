package com.example.demo.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.example.demo.model.LoginDTO;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/controller")
public class AuthController {
	
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	
	public AuthController(
			UserService userService,
			JwtUtil jwtUtil,
			PasswordEncoder passwordEncoder
			) {
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String message() {
		return "this is /";
	}
	
	
	//this is login function but we cannot name this "login" as that is reserved by the spring security.
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/checklogin") 
	public ResponseEntity<String> checkLogin(@RequestBody LoginDTO dto) {
		//System.out.println("Username = "+dto.getUsername());
		//System.out.println("password = "+dto.getPassword());
		User user = userService.getUserByUsername(dto.getUsername());
//		if(user==null) {
//			System.out.println("AuthController: Username not found");
//			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username");
//		}
//		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//			System.out.println("AuthController: password didnt match");
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Password");
//		}
		//instead of all these ifs, implemented single User Not found Exception
		System.out.println("AuthController: Login Success, generating token");
		
		//generate a token while login
		jwtUtil.generateToken(dto.getUsername());
		
		//get Current active token
		String token = jwtUtil.getToken();
		System.out.println("Token Generated : "+token);
		
		//get current username
		String username = jwtUtil.extractUsername(token);
		System.out.println("username inside token : "+username);
		
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		response.put("username", username);
		//return ResponseEntity.ok(token);
		return ResponseEntity.ok("{\"token\": \""+token+"\",\"username\": \""+username+"\"}");
	} 
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user_){
		//user_.setPassword(passwordEncoder.encode(user_.getPassword()));
		//User newUser = userService.createUser(user_);
		return ResponseEntity.ok(user_);
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/getUser")
	public ResponseEntity<User> getUserByUsername(@RequestParam String username_){
		User user = userService.getUserByUsername(username_);
		return ResponseEntity.ok(user);
	}
}
