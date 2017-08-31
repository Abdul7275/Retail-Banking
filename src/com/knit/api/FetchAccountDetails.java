package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
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
 * Servlet implementation class FetchAccountDetails
 */
@WebServlet("/getAcc")
public class FetchAccountDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchAccountDetails() {
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
		// starts doPost method here
		
				if (request.getSession().getAttribute("username") == null) {
				    response.sendRedirect( "/login"); // Not logged in, redirect to login page.
				    System.out.println("Not Logged In");
				}
				else {
				     // Logged in, just continue chain.
					System.out.println("Logged In");
				
				Account obj = new Gson().fromJson(request.getReader(), Account.class);
				System.out.println("Account ID from json data on server side is :"+obj.getAccount_ID());
				System.out.println("Customer ID from json data on server side is :"+obj.getCustomer_ID());
				System.out.println("Account Type from json data on server side is :"+obj.getAccount_Type());
				
				int k = 0; // decide which query to use.
				
				if(obj.getAccount_ID()!=null && obj.getCustomer_ID()!=null && obj.getAccount_Type()!=null)
				{
					k=0;
				}
				else if(obj.getAccount_Type()!=null && obj.getCustomer_ID()!=null )
				{
					k=1;
				
				}
				else if(obj.getAccount_ID()!=null)
				{
					k=0;
				}
				
				// now retrive customer id from database and send it to client starts here.
				//now get Customer_ID from database and send back to client in json form.
				Statement st;
				ResultSet resultset;
				Connection con1=ConnectionProvider.getConnection();
				
				
		        try {
		        	con1.setAutoCommit(false);
					st=con1.createStatement();
					String query="";
					if(k==0){
						query="Select * FROM ACCOUNT WHERE ACCOUNT_ID ='"+ obj.getAccount_ID() +"' AND DELETE_FLAG='"+"N"+"'";
					}
					else{
						query="Select * FROM ACCOUNT WHERE Customer_ID ='"+ obj.getCustomer_ID() +"' AND ACCOUNT_TYPE='"+ obj.getAccount_Type() +"' AND DELETE_FLAG='"+"N"+"'";
					}
					resultset = st.executeQuery(query);
					//resultset = st.executeQuery("select * from Customer where CUSTOMER_ID='" + Integer.parseInt(sqlVariable) + "'");
					System.out.println("query is : "+query); 
					Map<String, Object> data = new HashMap<>();
		            if(resultset.next())
		            {
		              int C_ID=resultset.getInt("CUSTOMER_ID");
		  		      
		  		    
		  		      
		  		      data.put("customer_ID", resultset.getInt("Customer_ID"));
		  		      data.put("account_Type", resultset.getString("ACCOUNT_TYPE"));
		  		      data.put("account_ID", resultset.getInt("ACCOUNT_ID"));
		  		      data.put("date", resultset.getTimestamp("CR_DATE"));
		  		      data.put("balance", resultset.getInt("BALANCE"));
		  		      

		  		      data.put("message", "fetched data successfully");
		  		      data.put("err", "false");
		  		      String json = new Gson().toJson(data);
		  		      

		  		      response.setContentType("application/json");
		  		      response.setCharacterEncoding("UTF-8");
		  		      response.getWriter().write(json);
		            }
		            else{
		            	data.put("err", "true");
		            	data.put("message", "No such customer exist with these details");
		            	System.out.println("no result set found with this condition");
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
