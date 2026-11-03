package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ExpenseSplitNotFoundException;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.model.Expense;
import com.example.demo.model.ExpenseSplit;
import com.example.demo.model.SplitDataDTO;
import com.example.demo.model.User;
import com.example.demo.repository.ExpenseSplitRepository;

@Service
public class ExpenseSplitService {
	private ExpenseSplitRepository expenseSplitRepository;
	private UserService userService;
	private GroupsService groupsService;
	
	public ExpenseSplitService(
			ExpenseSplitRepository expenseSplitRepository,
			UserService userService,
			GroupsService groupsService) {
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
	
	public void saveExpenseSplitByAll(Expense expense_, ArrayList<SplitDataDTO> splitsList) {
		for (SplitDataDTO split : splitsList) {
			ExpenseSplit expenseSplit = new ExpenseSplit();
			expenseSplit.setExpense(expense_);
			User user_ = userService.getUserByUsername(split.getUserName());
			expenseSplit.setUser(user_);
			expenseSplit.setUsername(user_.getUsername());
			if(expense_.getSplitBy().toString().equalsIgnoreCase("percent")) {
				float percent_ = split.getValue();
				//add security check for percent value. cannot be greater than 100
				expenseSplit.setSplitValue(percent_);
				float amount_ = expense_.getAmount() * percent_ / 100;
				expenseSplit.setAmount(amount_);
				try {
					expenseSplitRepository.save(expenseSplit);
					System.out.println("Expense Split Service : percent : Executed");
					
				}
				catch (Exception e) {
					throw new IllegalFieldException();
				}
			}
			else if(expense_.getSplitBy().toString() .equalsIgnoreCase("exact")) {
				//add security check for if user is a friend or not
				float exact_ = split.getValue();
				//add security check for percent value. cannot be greater than 100
				expenseSplit.setSplitValue(exact_);
				float amount_ = exact_;
				expenseSplit.setAmount(amount_);
				try {
					expenseSplitRepository.save(expenseSplit);
					System.out.println("Expense Split Service : exact : Executed");
				}
				catch (Exception e) {
					throw new IllegalFieldException();
				}
			}
			else if(expense_.getSplitBy().toString() .equalsIgnoreCase("equal")) {
				//add security check for if user is a friend or not
				float equal_ = split.getValue();
				//add security check for percent value. cannot be greater than 100
				expenseSplit.setSplitValue(equal_);
				float amount_ = equal_;
				expenseSplit.setAmount(amount_);
				try {
					expenseSplitRepository.save(expenseSplit);
					System.out.println("Expense Split Service : exact : Executed");
				}
				catch (Exception e) {
					throw new IllegalFieldException();
				}
			}
		}
	}


//	public void deleteExpenseSplitsByExpenseID(Long id_) {
////		expenseService.findExpenseByExpenseId(id_);//throws error if expense is not found
//		List<ExpenseSplit> list_ = expenseSplitRepository.findByExpense_ID(id_);
//		for(ExpenseSplit item : list_) {
//			expenseSplitRepository.delete(item);
//		}
//	}
}
