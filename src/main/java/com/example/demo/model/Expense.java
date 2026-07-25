package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.demo.model.EnumSplitBy;

@Entity
@Table(name = "expenses")
public class Expense {

	public Expense() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseID;

    /*
        Many expenses can belong to one group
    */
    @ManyToOne
    @JoinColumn(name = "groupID", nullable = false)
    private Group group;
    
    

	@Column(name="amount", nullable = false)
    private Long amount;

	@Column(nullable = false)
    private String expenseDesc;

    @Column(nullable = false)
    private String paidByUserName;

    public String getPaidByUserName() {
		return paidByUserName;
	}

	public void setPaidByUserName(String paidByUserName) {
		this.paidByUserName = paidByUserName;
	}

	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumSplitBy splitBy;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToMany
    @JoinTable(
        name = "expense_participants",
        joinColumns = @JoinColumn(name = "expense_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

	public Long getExpenseID() {
		return expenseID;
	}

	public void setExpenseID(Long expenseID) {
		this.expenseID = expenseID;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getExpenseDesc() {
		return expenseDesc;
	}

	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}

	public EnumSplitBy getSplitBy() {
		return splitBy;
	}

	public void setSplitBy(EnumSplitBy splitBy) {
		this.splitBy = splitBy;
	}

	public LocalDate getDate() {
		return date;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	

}
