package com.knit.api;

import com.google.gson.Gson;

public class Gson_Testing {

	/**
	 * @param args
	 */  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUser_name("alok_shakya");
		user.setPassword("alok789");
		
		Gson gson = new Gson();
		String user_json = gson.toJson(user); //serialization
		System.out.println(user_json);
		

	}

}
