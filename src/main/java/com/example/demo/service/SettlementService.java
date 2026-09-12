package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ExpenseNotFoundException;
import com.example.demo.model.Expense;
import com.example.demo.model.ExpenseSplit;
import com.example.demo.model.SettlementDTO;

@Service
public class SettlementService {

	private ExpenseService expenseService;
	private ExpenseSplitService expenseSplitService;
	
	public SettlementService(ExpenseService expenseService,
			ExpenseSplitService expenseSplitService ) {
		this.expenseService = expenseService;
		this.expenseSplitService = expenseSplitService;
	}
	
	public List<SettlementDTO> calculateSettlement(Long GroupID_){
		List<Expense> expenses = expenseService.findExpenseByGroupId(GroupID_).get();
		if(expenses.isEmpty()) {
			throw new ExpenseNotFoundException();
		}
		Map<String, Double> balance = new HashMap<>();
		for(Expense expense: expenses) {
			String payer = expense.getPaidByUserName();
			balance.putIfAbsent(payer, 0.0);
			balance.put(payer, balance.get(payer)+expense.getAmount());
			
			List<ExpenseSplit> splits = expenseSplitService.findExpenseSplitByExpenseId(expense.getExpenseID());
			for (ExpenseSplit split: splits) {
				String member = split.getUsername();
				balance.putIfAbsent(member, 0.0);
				balance.put(member, balance.get(member)-split.getAmount());	
			}	
		}
		
		List<Map.Entry<String, Double>> creditors = new ArrayList();
	    List<Map.Entry<String, Double>> debtors   = new ArrayList<>();
	    for(Map.Entry<String, Double> entry : balance.entrySet()) {
	    	if (entry.getValue() > 0)
	            creditors.add(entry);
	        else if (entry.getValue() < 0)
	            debtors.add(entry);
	    }
	    List<SettlementDTO> result = new ArrayList<>();
	    int i=0;
	    int j=0;
	    while(i<debtors.size() && j< creditors.size()) {
	    	double debt = -debtors.get(i).getValue();
	    	double credit = creditors.get(j).getValue();
	    	double amount = Math.min(debt, credit);
	    	result.add(new SettlementDTO(
	    			debtors.get(i).getKey(),
	    			creditors.get(j).getKey(),
	    			amount));
	    	System.out.println("------> result.add > "+debtors.get(i).getKey());
	    	System.out.println("------> result.add > "+creditors.get(j).getKey());
	    	System.out.println("------> result.add > "+amount);
	    	debtors.get(i).setValue(-(debt-amount));
	    	creditors.get(j).setValue(credit-amount);
	    	
	    	if(-debtors.get(i).getValue()<0.01) i++;
	    	if(creditors.get(j).getValue()<0.01) j++;
	    }
		return result;
	}
}
