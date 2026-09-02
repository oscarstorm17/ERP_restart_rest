package com.example.demo.controller;

import java.util.ArrayList;
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
import com.example.demo.model.ExpenseSplitDTO;
import com.example.demo.model.SplitDataDTO;
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
	@PostMapping("/saveExpenseSplit/save")
	public ExpenseSplitDTO saveExpenseSplitByAll(@RequestBody ExpenseSplitDTO request) { 
		// will contain expenseID, 
		//splitBy (percent/exact/equal),  
		//splits (SplitDataDTO object)	- username
		//								- percent value (30%)
		System.out.println("EXPENSE Split Controller : save : executed");
		Expense expense = expenseService.findExpenseByExpenseId(request.getExpenseID()).get();
		ArrayList<SplitDataDTO> splits =  request.getSplits();
        expenseSplitService.saveExpenseSplitByAll(expense, splits);
        System.out.println("EXPENSE Split Controller : save : completed");
        return request;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	



}
