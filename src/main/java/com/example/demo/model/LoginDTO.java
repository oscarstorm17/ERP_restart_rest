package com.example.demo.model;


public class LoginDTO {

	//Login Request DTO
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	//Login Response DTO
	private String token;
	public LoginDTO(String token) {
		this.token=token;
	}
	public String getToken() {
		return token;
	}
	
	
}
