/**
 * 
 */
package com.knit.api;

/**
 * @author ALOK SHAKYA
 *
 */
public class CAccount {
	private String customer_ID;
	private String acc_type;
	public String getCustomer_ID() {
		return customer_ID;
	}
	public void setCustomer_ID(String customer_ID) {
		this.customer_ID = customer_ID;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	private int balance;
	
	

}
