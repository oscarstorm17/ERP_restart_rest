package com.example.demo.exceptions;

public class ExpenseNotFoundException extends RuntimeException {

	public ExpenseNotFoundException() {
		// TODO Auto-generated constructor stub
		super("Expense Not Found");
	}

}
