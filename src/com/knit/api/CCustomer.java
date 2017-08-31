/**
 * 
 */
package com.knit.api;

/**
 * @author ALOK SHAKYA
 *
 */
public class CCustomer {
	private String SSN_ID;
	private String name;
	private String age;
	private String address;
	public String getSSN_ID() {
		return SSN_ID;
	}
	public void setSSN_ID(String sSN_ID) {
		SSN_ID = sSN_ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
