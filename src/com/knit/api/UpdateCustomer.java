package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connect.ConnectionProvider;

/**
 * Servlet implementation class UpdateCustomer
 */
@WebServlet("/updateCust")
public class UpdateCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (request.getSession().getAttribute("username") == null && request.getSession().getAttribute("role")=="executive") {
		    response.sendRedirect( "/"); // Not logged in, redirect to login page.
		    System.out.println("Not Logged In");
		}
		else {
		     // Logged in, just continue chain.
			System.out.println("Logged In");
		
		UpdateDetails obj = new Gson().fromJson(request.getReader(), UpdateDetails.class);
		System.out.println("SSN_ID from json data on server side is :"+obj.getSSN_ID());
		System.out.println("customer_ID from json data on server side is :"+obj.getCustomer_ID());
		System.out.println("Name from json data on server side is :"+obj.getName());
		System.out.println("Age from json data on server side is :"+obj.getAge());
		System.out.println("Address from json data on server side is :"+obj.getAddress());
		
		
		
		

		
		try {
			Connection con=ConnectionProvider.getConnection();
			
        	con.setAutoCommit(false);
        	//insert using prepared statements
			String query="UPDATE CUSTOMER SET name = ?, address = ?, age = ? WHERE Customer_ID = ?";
			// create the Oracle insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		    
		      preparedStmt.setString (1, obj.getName());
		      preparedStmt.setString   (2, obj.getAddress());
		      preparedStmt.setInt(3, Integer.parseInt(obj.getAge()));
		      preparedStmt.setInt(4, Integer.parseInt(obj.getCustomer_ID()));

		      // execute the preparedstatement
		      preparedStmt.execute();
		      
		      
		      con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// updating data in database has ended here
	

		
		
	}//ends else statement
		
	}

}
