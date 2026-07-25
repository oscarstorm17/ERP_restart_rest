package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ExpenseSplitNotFoundException;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.model.Expense;
import com.example.demo.model.ExpenseSplit;
import com.example.demo.model.User;
import com.example.demo.repository.ExpenseSplitRepository;

@Service
public class ExpenseSplitService {
	private ExpenseService expenseService;
	private ExpenseSplitRepository expenseSplitRepository;
	private UserService userService;
	private GroupsService groupsService;
	
	public ExpenseSplitService(
			ExpenseService expenseService,
			ExpenseSplitRepository expenseSplitRepository,
			UserService userService,
			GroupsService groupsService) {
		// TODO Auto-generated constructor stub
		this.expenseService=expenseService;
		this.expenseSplitRepository=expenseSplitRepository;
		this.groupsService=groupsService;
		this.userService=userService;
	}

	public ExpenseSplit findExpenseSplitBySplitId(Long id_){
		Optional<ExpenseSplit> split = expenseSplitRepository.findBySplitID(id_); 
		if(split.isEmpty()) {
			throw new ExpenseSplitNotFoundException();
		}
		return split.get();
	}
	
	public List<ExpenseSplit> findExpenseSplitByExpenseId(Long id_){ // returns a list of expenseSplit
		List<ExpenseSplit> list = expenseSplitRepository.findByExpense_expenseID(id_);
		if(list.isEmpty()) {
			throw new ExpenseSplitNotFoundException();
		}
		return list;
	}
	
	public List<ExpenseSplit> findExpenseSplitByUserId(Long id_){
		List<ExpenseSplit> list = expenseSplitRepository.findByUser_Id(id_);
		if(list.isEmpty()) {
			throw new ExpenseSplitNotFoundException();
		}
		return list;
	}
	public ExpenseSplit saveExpenseSplitByPercent(Expense expense_, User user_, float percent_){
		//item_ contains expenseID, userID, splitValue
		//finalAmount to be calculated here
		
		ExpenseSplit expenseSplit = new ExpenseSplit();
		expenseSplit.setUser(user_);
		expenseSplit.setExpense(expense_);
		expenseSplit.setSplitValue(percent_);
		float amount = expense_.getAmount() * percent_ / 100;
        expenseSplit.setAmount(amount);
		try {
			return expenseSplitRepository.save(expenseSplit);
		}
		catch (Exception e) {
			throw new IllegalFieldException();
		}
	}
	
	public ExpenseSplit saveExpenseSplitByExact(Expense expense_, User user_, float exactAmount_) {
		ExpenseSplit expenseSplit = new ExpenseSplit();
		expenseSplit.setUser(user_);
		expenseSplit.setExpense(expense_);
		expenseSplit.setSplitValue(exactAmount_);
		float amount = exactAmount_;
        expenseSplit.setAmount(amount);
		try {
			return expenseSplitRepository.save(expenseSplit);
		}
		catch (Exception e) {
			throw new IllegalFieldException();
		}
	}
	
	public ExpenseSplit saveExpenseSplitByEqual(Expense expense_, User user_, float equalAmount_) {
		ExpenseSplit expenseSplit = new ExpenseSplit();
		expenseSplit.setUser(user_);
		expenseSplit.setExpense(expense_);
		expenseSplit.setSplitValue(equalAmount_);
		float amount = equalAmount_;
        expenseSplit.setAmount(amount);
		try {
			return expenseSplitRepository.save(expenseSplit);
		}
		catch (Exception e) {
			throw new IllegalFieldException();
		}
	}
	
}
