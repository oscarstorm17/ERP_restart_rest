package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ExpenseNotFoundException;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.model.Expense;
import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.GroupRepository;

@Service
public class ExpenseService {

	private ExpenseRepository expenseRepository;
	private GroupRepository groupRepository;
	private GroupsService groupsService;
	private UserService	userService;
	public ExpenseService(ExpenseRepository expenseRepository, 
			GroupRepository groupRepository,
			GroupsService groupsService,
			UserService userService,
			ExpenseSplitService expenseSplitService) {
		this.expenseRepository = expenseRepository;
		this.groupRepository=groupRepository;
		this.groupsService=groupsService;
		this.userService=userService;
	}
	
	
	public Expense addExpense(Expense expense_){
		if(expense_.getExpenseDesc().isEmpty() || expense_.getSplitBy().toString().isEmpty()) {
			throw new IllegalFieldException();
		}
		Long id_ = expense_.getGroup().getGroupID();
		//need to add security checkes for groupID, paidByUsername, splitBY
		Group group_ = groupsService.getGroupById(id_);
		//User user_ = userService.getUserByUsername(expense_.getPaidByUserName());
		//paid by user exists.
		//expense_.setSplitBy(expense_.getSplitBy().toString().toUpperCase());
		expenseRepository.save(expense_);
		return  expense_; 
	}
	
	public Expense findExpenseByExpenseId(Long id_){
//		Expense expense = expenseRepository.findByExpenseID(id_).;
//		return expenseRepository.findByExpenseID(id_)
//				.orElseThrow(() -> new ExpenseNotFoundException());
		return null;
	}
	
	public Optional<List<Expense>> findExpenseByGroupId(Long id_){
		Group group_ = groupsService.getGroupById(id_);
		List<Expense> list_ = expenseRepository.findByGroup(group_);
		if(list_.isEmpty()) {
			throw new ExpenseNotFoundException();
		}
		return Optional.ofNullable(list_);
	}
	
//	public void deleteExpenseByExpenseID(Long id_) {
//		Expense expense = findExpenseByExpenseId(id_); // throws exception if expense not found
//		expenseSplitService.deleteExpenseSplitsByExpenseID(id_);
//		expenseRepository.delete(expense);
//		
//	}
	

	
}
