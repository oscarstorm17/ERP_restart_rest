package com.example.demo.model;

import java.util.ArrayList;


public class ExpenseSplitDTO {

	public ExpenseSplitDTO() {
	}
	
	private Long expenseID;
    private String splitBy;
    private ArrayList<SplitDataDTO> splits;
    
    
	public Long getExpenseID() {
		return expenseID;
	}
	public void setExpenseID(Long expenseID) {
		this.expenseID = expenseID;
	}
	public String getSplitBy() {
		return splitBy;
	}
	public void setSplitBy(String splitBy) {
		this.splitBy = splitBy;
	}
	public ArrayList<SplitDataDTO> getSplits() {
		return splits;
	}
	public void setSplits(ArrayList<SplitDataDTO> splits) {
		this.splits = splits;
	}
	

    
}
