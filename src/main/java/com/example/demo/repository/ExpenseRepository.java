package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Expense;
import com.example.demo.model.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	Optional<Expense>  findByExpenseID(Long expenseID);
	List<Expense> findByGroup(Group group);
	
}
