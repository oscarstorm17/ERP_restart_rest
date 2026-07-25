package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	public GlobalExceptionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoud(UserNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(IllegalFieldException.class)
	public ResponseEntity<String> handleUserIllegalFieldException(IllegalFieldException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(GroupNotFoundException.class)
	public ResponseEntity<String> handleGroupNotFoundException(GroupNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	@ExceptionHandler(FriendsNotFoundException.class)
	public ResponseEntity<String> handleFriendsNotFoundException(FriendsNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Friends Not Found");
	}
	
	@ExceptionHandler(ExpenseNotFoundException.class )
		public ResponseEntity<String> handleExpenseNotFoundException(ExpenseNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense Not Found");
	}
	
	@ExceptionHandler(ExpenseSplitNotFoundException.class)
	public ResponseEntity<String> handleExpenseSplitNotFoundException(ExpenseSplitNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
}
