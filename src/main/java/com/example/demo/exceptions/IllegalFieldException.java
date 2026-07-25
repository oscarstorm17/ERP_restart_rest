package com.example.demo.exceptions;

public class IllegalFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalFieldException() {
		// TODO Auto-generated constructor stub
		super("Field is missing or illegal data");
	}

}
