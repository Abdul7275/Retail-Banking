package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
 * Servlet implementation class Deposit
 */
@WebServlet("/deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
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
		if (request.getSession().getAttribute("username") == null && request.getSession().getAttribute("role")=="cashier") {
		    response.sendRedirect( "/"); // Not logged in, redirect to login page.
		    System.out.println("Not Logged In");
		}
		else{
			Transfer obj = new Gson().fromJson(request.getReader(), Transfer.class);
			System.out.println("Account_ID from json data on server side is :"+obj.getAccount_ID());
			System.out.println("Amount from json data on server side is :"+obj.getAmount());
			int p_B=0,c_B=0,customer_ID=0;
			
		
			//get previous balance
			try{
				System.out.println("Inside try1 of getting prev balance");
				Connection con=ConnectionProvider.getConnection();
				con.setAutoCommit(false);
	        	Statement st=con.createStatement();
				ResultSet resultset = st.executeQuery("select * from Account where Account_ID='" + obj.getAccount_ID() + "'");
				if(resultset.next()){
					p_B=resultset.getInt("Balance");
					customer_ID=resultset.getInt("Customer_ID");
					System.out.println("previous balance "+p_B);
					c_B=p_B+obj.getAmount();
				}
				con.close();
				// second connection and start
				Connection con2=ConnectionProvider.getConnection();
				System.out.println("Inside try2 of inserting value in transaction table ");
				con2.setAutoCommit(false);
				//insert using prepared statements
				String query="INSERT INTO Transaction (ACCOUNT_ID, C_D, TR_DATE, Amount, description, CR_Balance)"
				+"VALUES(?,?,?,?,?,?)";
				// create the Oracle insert preparedstatement
			      PreparedStatement preparedStmt = con2.prepareStatement(query);
			      System.out.println("Inside try2 of inserting value in transaction table ");
			      preparedStmt.setInt (1, obj.getAccount_ID());
			      System.out.println("Inside try2 of inserting value in transaction table ");
			      preparedStmt.setString (2, "credit");
			      System.out.println("Inside try2 of inserting value in transaction table ");
			      preparedStmt.setTimestamp   (3, new Timestamp(System.currentTimeMillis()));
			      System.out.println("Inside try2 of inserting value in transaction table ");
			      preparedStmt.setInt(4, obj.getAmount());
			      System.out.println("Inside try2 of inserting value in transaction table ");
			      preparedStmt.setString    (5, "deposit");
			      System.out.println("Inside try2 of inserting value in transaction table ");
			      preparedStmt.setInt    (6, c_B);
			      System.out.println("Inside try2 of inserting value in transaction table ");

			      // execute the preparedstatement
			      preparedStmt.execute();
			      con2.close();

				//third connection
			      Connection con3=ConnectionProvider.getConnection();
					System.out.println("Inside try3 of updating prev balance");
					//now update account table also
				      String query1="UPDATE Account SET Balance = ? WHERE Account_ID = ?";
						// create the Oracle insert preparedstatement
					      PreparedStatement preparedStmt2 = con3.prepareStatement(query1);
					    
					      preparedStmt2.setInt (1, c_B);
					      preparedStmt2.setInt   (2, obj.getAccount_ID());
					      // execute the preparedstatement
					      preparedStmt2.execute();
					      con3.close();
					      //now send json data
					      Map<String, String> data = new HashMap<>();
			  		      data.put("prev_B", Integer.toString(p_B));
			  		      data.put("cr_B", Integer.toString(c_B));
			  		      data.put("account_ID", Integer.toString(obj.getAccount_ID()));
			  		      data.put("customer_ID", Integer.toString(customer_ID));
			  		      data.put("err", "false");
			  		      data.put("message", "amount deposited successfully");
			  		      String json = new Gson().toJson(data);
			  		      

			  		      response.setContentType("application/json");
			  		      response.setCharacterEncoding("UTF-8");
			  		      response.getWriter().write(json);

				
			}
			catch(SQLException e){
				Map<String, String> data = new HashMap<>();
	  		     
	  		      data.put("account_ID", Integer.toString(obj.getAccount_ID()));
	  		      data.put("customer_ID", Integer.toString(customer_ID));
	  		      data.put("err", "true");
	  		      data.put("message", "amount does not deposited successfully Account ID does not exist");
	  		      String json = new Gson().toJson(data);
	  		      

	  		      response.setContentType("application/json");
	  		      response.setCharacterEncoding("UTF-8");
	  		      response.getWriter().write(json);
				e.printStackTrace();
			}
			
			
            // update account table and insert value in transaction table
			
			


			
		}

	}

}
