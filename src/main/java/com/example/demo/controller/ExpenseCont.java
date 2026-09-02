package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Expense;
import com.example.demo.service.ExpenseService;

@RestController
@RequestMapping("/e")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseCont {
	private ExpenseService expenseService;
	
	public ExpenseCont(ExpenseService expenseService) {
		// TODO Auto-generated constructor stub
		this.expenseService=expenseService;
	}
	
	@GetMapping("/check")
		public ResponseEntity<String> check() {
			System.out.println("checking new controller for expense success");
			
			return ResponseEntity.ok("working");
		}
	
	@PostMapping("/addExpense") 
	//expects 
	public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense_){
		expenseService.addExpense(expense_);
		return ResponseEntity.ok(expense_);
	}
	
	@GetMapping("/getExpenses")
	public ResponseEntity<List<Expense>> getExpensesByGroupID(@RequestParam Long group_id_){
		List<Expense> expenseList = expenseService.findExpenseByGroupId(group_id_).get();
		return ResponseEntity.ok(expenseList);
	}
	

}
