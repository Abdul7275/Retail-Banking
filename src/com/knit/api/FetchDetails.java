package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
//import java.sql.PreparedStatement;
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
 * Servlet implementation class FetchDetails
 */
@WebServlet("/getCust")
public class FetchDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// starts doPost method here
		
		if (request.getSession().getAttribute("username") == null) {
		    response.sendRedirect( "/login"); // Not logged in, redirect to login page.
		    System.out.println("Not Logged In");
		}
		else {
		     // Logged in, just continue chain.
			System.out.println("Logged In");
		
		FetchUpdateQuery obj = new Gson().fromJson(request.getReader(), FetchUpdateQuery.class);
		System.out.println("SSN_ID from json data on server side is :"+obj.getSSN_ID());
		System.out.println("Name from json data on server side is :"+obj.getCustomer_ID());
		String ssn_id=obj.getSSN_ID();
		String c_id=obj.getCustomer_ID();
		String sqlVariable="";
		String sqlCondition="";
		if(ssn_id!=null && c_id!=null)
		{
		 sqlVariable=c_id;
		 sqlCondition="CUSTOMER_ID";
		}
		else if(ssn_id!=null)
		{
			sqlVariable=ssn_id;
			sqlCondition = "SSN_ID";
		}
		else if(c_id!=null)
		{
			sqlVariable=c_id;
			sqlCondition = "CUSTOMER_ID";
		}
		
		// now retrive customer id from database and send it to client starts here.
		//now get Customer_ID from database and send back to client in json form.
		Statement st;
		ResultSet resultset,resultset1;
		Connection con1=ConnectionProvider.getConnection();
		
		
        try {
        	con1.setAutoCommit(false);
			st=con1.createStatement();
			resultset = st.executeQuery("select * from Customer where " + sqlCondition + "='" + Integer.parseInt(sqlVariable) + "'");
			//resultset1 = st.executeQuery("select * from Customer where " + sqlCondition + "='" + Integer.parseInt(sqlVariable) + "'");
			
			//resultset = st.executeQuery("select * from Customer where CUSTOMER_ID='" + Integer.parseInt(sqlVariable) + "'");
			System.out.println("select * from Customer where '" + sqlCondition + "'='" + Integer.parseInt(sqlVariable) + "'"); 
			
			
			 if(resultset.next())
            {
				 System.out.println("delete flag inside only if"+resultset.getString("Delete_Flag"));
				 String co=resultset.getString("Delete_Flag");
				 if(co.equalsIgnoreCase("Y")){
						Map<String, String> data = new HashMap<>();
						data.put("err", "true");
						System.out.println("delete flag inside if if"+resultset.getString("Delete_Flag"));
		            	data.put("message", "Customer has been deleted");
		            	System.out.println("customer deleted fromif inside if");
		            	String json = new Gson().toJson(data);
		    		      response.setContentType("application/json");
		    		      response.setCharacterEncoding("UTF-8");
		    		      response.getWriter().write(json);
					}
			 else{
				 Map<String, String> data = new HashMap<>();
	              int C_ID=resultset.getInt("CUSTOMER_ID");
	  		      System.out.println("Customer Id for "+obj.getSSN_ID()+" is :"+C_ID);
	  		      String add=resultset.getString("Address");
	  		    System.out.println("Address is :"+add);
	  		  System.out.println("found if else");
	  		      String[] ad=add.split("[$]");
	  		      String adl1=ad[0];
	  		      String adl2=ad[1];
	  		      String city=ad[2];
	  		      String state=ad[3];
	  		      
	  		      data.put("customer_ID", Integer.toString(C_ID));
	  		      data.put("SSN_ID", Integer.toString(resultset.getInt("SSN_ID")));
	  		      data.put("age", Integer.toString(resultset.getInt("Age")));
	  		      data.put("adl1", adl1);
	  		      data.put("adl2", adl2);
	  		      data.put("city", city);
	  		      data.put("state", state);
	  		      data.put("name", resultset.getString("Name"));

	  		      data.put("message", "fetched");
	  		      data.put("err", "false");
	  		      String json = new Gson().toJson(data);
	  		      

	  		      response.setContentType("application/json");
	  		      response.setCharacterEncoding("UTF-8");
	  		      response.getWriter().write(json);
			 }
				
            }
            else{
            	Map<String, String> data = new HashMap<>();
            	data.put("err", "true");
            	data.put("message", "No such customer exist with these details");
            	System.out.println("no result set found with this condition from else");
            	String json = new Gson().toJson(data);
    		      response.setContentType("application/json");
    		      response.setCharacterEncoding("UTF-8");
    		      response.getWriter().write(json);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now retrive customer id from database and send it to client ends here.

		
		
	}//ends else statement
		
		//ends doPost method here
	}

}
