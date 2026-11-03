package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenseSplit",
	indexes = {
			@Index(name = "idx_expense", columnList = "expenseid"),
			@Index(name="idx_user", columnList = "userID")
	}
	)
public class ExpenseSplit {

	public ExpenseSplit() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long splitID;
	
	@ManyToOne
    @JoinColumn(name = "expenseid", nullable = false)
    private Expense expense; // object

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user; // person/ user / userID // by default it takes userID
    
    @Column(name = "username", nullable= false)
    private String username;
	
	@Column(name="splitvalue", nullable = false)
	private float splitValue;  //  percent or share (30%)
	
	@Column(name="amount", nullable = false)
	private float splitAmount; //amount owed    (455)

	
	
	
	
	
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
		return splitAmount;
	}

	public void setAmount(float amount) {
		this.splitAmount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
