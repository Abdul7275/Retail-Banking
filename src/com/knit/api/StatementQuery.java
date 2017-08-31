package com.knit.api;

public class StatementQuery {
	private int no_of_transaction;
	private int account_ID;
	private String start_date;
	private String end_date;
	public int getNo_of_transaction() {
		return no_of_transaction;
	}
	public void setNo_of_transaction(int no_of_transaction) {
		this.no_of_transaction = no_of_transaction;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getAccount_ID() {
		return account_ID;
	}
	public void setAccount_ID(int account_ID) {
		this.account_ID = account_ID;
	}

}
