package com.example.demo.model;

public class SettlementDTO {

	public SettlementDTO() {
		// TODO Auto-generated constructor stub
	}    
	public SettlementDTO(String fromUser, String toUser, double amount) {
		super();
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.amount = amount;
	}

	private String fromUser;
    private String toUser;
    private double amount;
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
