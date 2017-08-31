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
//import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import connect.ConnectionProvider;

/**
 * Servlet implementation class CreateCustomer
 */
@WebServlet("/createCust")
public class CreateCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("username") == null && request.getSession().getAttribute("role")=="executive") {
		    response.sendRedirect( "/"); // Not logged in, redirect to login page.
		    System.out.println("Not Logged In");
		}
		else {
		     // Logged in, just continue chain.
			System.out.println("Logged In");
		
		CCustomer obj = new Gson().fromJson(request.getReader(), CCustomer.class);
		System.out.println("SSN_ID from json data on server side is :"+obj.getSSN_ID());
		System.out.println("Name from json data on server side is :"+obj.getName());
		System.out.println("Age from json data on server side is :"+obj.getAge());
		System.out.println("Address from json data on server side is :"+obj.getAddress());
		
		// now store this data in data starts here
		
		
		

		
		try {
			Connection con=ConnectionProvider.getConnection();
			
        	con.setAutoCommit(false);
        	//insert using prepared statements
			String query="INSERT INTO CUSTOMER (SSN_ID, NAME, ADDRESS, AGE,DELETE_FLAG)"
			+"VALUES(?,?,?,?,?)";
			// create the Oracle insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, Integer.parseInt(obj.getSSN_ID()));
		      preparedStmt.setString (2, obj.getName());
		      preparedStmt.setString   (3, obj.getAddress());
		      preparedStmt.setInt(4, Integer.parseInt(obj.getAge()));
		      preparedStmt.setString    (5, "N");

		      // execute the preparedstatement
		      preparedStmt.execute();
		      
		      
		      con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// storing data in database has ended here
		// now retrive customer id from database and send it to client starts here.
		//now get Customer_ID from database and send back to client in json form.
		Statement st;
		ResultSet resultset;
		Connection con1=ConnectionProvider.getConnection();
		
		
        try {
        	con1.setAutoCommit(false);
			st=con1.createStatement();
			resultset = st.executeQuery("select * from Customer where SSN_ID='" + obj.getSSN_ID() + "'");
			 Map<String, String> data = new HashMap<>();
            if(resultset.next())
            {
              int C_ID=resultset.getInt("CUSTOMER_ID");
  		      System.out.println("Customer Id for "+obj.getSSN_ID()+" is :"+C_ID);
  		     
  		      data.put("Customer_ID", Integer.toString(C_ID));
  		      data.put("message", "user created successfully");
  		      String json = new Gson().toJson(data);
  		      

  		      response.setContentType("application/json");
  		      response.setCharacterEncoding("UTF-8");
  		      response.getWriter().write(json);
            }
            else{
            	data.put("message", "user is not created successfully");
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now retrive customer id from database and send it to client ends here.

		
		
	}//ends else statement
	}// ends doPost method

}
