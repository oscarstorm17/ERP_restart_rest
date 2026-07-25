package com.example.demo.exceptions;

public class GroupNotFoundException extends RuntimeException{

	public GroupNotFoundException(Long id_) {
		// TODO Auto-generated constructor stub
		super("Group Not Found with id: "+id_);
	}
	
	public GroupNotFoundException(String username_) {
		super("Group Not Found with username: "+username_);
	}

}
