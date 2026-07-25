package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.EnumSplitBy;
import com.example.demo.model.Expense;
import com.example.demo.model.ExpenseSplit;
import com.example.demo.model.User;
import com.example.demo.repository.ExpenseSplitRepository;
import com.example.demo.service.ExpenseService;
import com.example.demo.service.ExpenseSplitService;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/expenseSplit")
public class ExpenseSplitController {
	private ExpenseService expenseService;
	private ExpenseSplitService expenseSplitService;
	private UserService userService;
	
	
	public ExpenseSplitController(
			ExpenseSplitService expenseSplitService,
			ExpenseService expenseService,
			UserService userService) {
		// TODO Auto-generated constructor stub
		this.expenseService=expenseService;
		this.expenseSplitService=expenseSplitService;
		this.userService=userService;
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@GetMapping("/expenseid")
	public List<ExpenseSplit> findExpenseSplitByExpenseId(@RequestParam Long id_){		//return a list of expensSplit
		List<ExpenseSplit> list = expenseSplitService.findExpenseSplitByExpenseId(id_);
		return list;
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@GetMapping("/splitid")
	public ExpenseSplit findExpenseSplitBySplitId(@RequestParam Long id_) {				//returns a single expenseSplit
		ExpenseSplit expenseSplit = expenseSplitService.findExpenseSplitBySplitId(id_);
		return expenseSplit;
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@GetMapping("/userid")
	public List<ExpenseSplit> findExpenseSplitByUserId(@RequestParam Long id_){
		List<ExpenseSplit> list = expenseSplitService.findExpenseSplitByUserId(id_);
		return list;
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/saveExpenseSplit/percent")
	public ExpenseSplit saveExpenseSplitByPercent(
			@RequestParam Long ExpenseId_,
			@RequestParam Long UserID_,
			@RequestParam Long percent_) { 
		// will contain expenseid, userid, splitValue
		//amount to be calculated in service layer	
		Expense expense = expenseService.findExpenseByExpenseId(ExpenseId_).get();
        User user = userService.getUserById(UserID_);

        return expenseSplitService.saveExpenseSplitByPercent(expense, user, percent_);        
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/saveExpenseSplit/exact")
	public ExpenseSplit saveExpenseSplitByExact(
			@RequestParam Long ExpenseId_,
			@RequestParam Long UserID_,
			@RequestParam Long exact_) {
			
			Expense expense = expenseService.findExpenseByExpenseId(ExpenseId_).get();
	        User user = userService.getUserById(UserID_);
	        return expenseSplitService.saveExpenseSplitByPercent(expense, user, exact_);        
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/saveExpenseSplit/equal")
	public ExpenseSplit saveExpenseSplitByEqual(
			@RequestParam Long ExpenseId_,
			@RequestParam Long UserID_,
			@RequestParam Long equal_) {
			
			Expense expense = expenseService.findExpenseByExpenseId(ExpenseId_).get();
	        User user = userService.getUserById(UserID_);
	        return expenseSplitService.saveExpenseSplitByPercent(expense, user, equal_);        
	}
	



}
