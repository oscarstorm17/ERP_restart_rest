package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenseSplit")
public class ExpenseSplit {

	public ExpenseSplit() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long splitID;
	
	@ManyToOne
    @JoinColumn(name = "expenseID", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
	
	@Column(name="splitvalue", nullable = false)
	private float splitValue;  //  percent or share
	
	@Column(name="amount", nullable = false)
	private float amount; //amount owed

	
	
	
	
	
	public Long getSplitID() {
		return splitID;
	}

	public void setSplitID(Long splitID) {
		this.splitID = splitID;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public float getSplitValue() {
		return splitValue;
	}

	public void setSplitValue(float splitValue) {
		this.splitValue = splitValue;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
	
}
