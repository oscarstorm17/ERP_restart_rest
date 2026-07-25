package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ExpenseSplit;

@Repository
public interface ExpenseSplitRepository extends  JpaRepository<ExpenseSplit, Long>{
	List<ExpenseSplit>  findByExpense_expenseID(Long expenseID);
	Optional<ExpenseSplit>  findBySplitID(Long splitID);
	List<ExpenseSplit> findByUser_Id(Long userID);
	
}
