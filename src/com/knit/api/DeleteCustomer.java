package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connect.ConnectionProvider;

/**
 * Servlet implementation class DeleteCustomer
 */
@WebServlet("/deleteCust")
public class DeleteCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCustomer() {
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
		System.out.println("SSN_ID from json data in delete on server side is :"+obj.getSSN_ID());
		System.out.println("customer_ID from json in delete data on server side is :"+obj.getCustomer_ID());
		System.out.println("Name from json data on in delete server side is :"+obj.getName());
		System.out.println("Age from json data on in delete server side is :"+obj.getAge());
		System.out.println("Address from json data on in delete server side is :"+obj.getAddress());
		
		// now update this data in data starts here
		
		
		

		
		try {
			Connection con=ConnectionProvider.getConnection();
			
        	con.setAutoCommit(false);
        	//insert using prepared statements
			String query="UPDATE CUSTOMER SET DELETE_FLAG = ? WHERE Customer_ID = ?";
			// create the Oracle insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		    
		      preparedStmt.setString (1, "Y");   
		      preparedStmt.setInt(2, Integer.parseInt(obj.getCustomer_ID()));

		      // execute the preparedstatement
		      preparedStmt.execute();
		      
		      
		      con.close();
		      Connection con1=ConnectionProvider.getConnection();
				
	        	con1.setAutoCommit(false);
	        	//insert using prepared statements
				String query1="UPDATE ACCOUNT SET DELETE_FLAG = ? WHERE Customer_ID = ?";
				// create the Oracle insert preparedstatement
			      PreparedStatement preparedStmt1 = con1.prepareStatement(query1);
			    
			      preparedStmt1.setString (1, "Y");   
			      preparedStmt1.setInt(2, Integer.parseInt(obj.getCustomer_ID()));

			      // execute the preparedstatement
			      preparedStmt1.execute();
			      
			      
			      con1.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// updating data in database has ended here
	

		
		
	}//ends else statement
	}

}
