package com.example.demo.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long id_) {
		// TODO Auto-generated constructor stub
		super("User not found with ID: "+id_);
	}
	
	public UserNotFoundException(String param) {
		super("User not found with param: "+param);
	}

}
