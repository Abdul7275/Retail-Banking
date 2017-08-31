package com.knit.api;

public class Account {
	private String customer_ID;
	private String account_Type;
	private String account_ID;
	private int balance;
	public String getCustomer_ID() {
		return customer_ID;
	}
	public void setCustomer_ID(String customer_ID) {
		this.customer_ID = customer_ID;
	}
	public String getAccount_Type() {
		return account_Type;
	}
	public void setAccount_Type(String account_Type) {
		this.account_Type = account_Type;
	}
	public String getAccount_ID() {
		return account_ID;
	}
	public void setAccount_ID(String account_ID) {
		this.account_ID = account_ID;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	

}
