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
 * Servlet implementation class TransferServlet
 */
@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferServlet() {
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
			System.out.println("Target Account from json data on server side is :"+obj.getT_account_ID());
			int s_p_B=0,s_c_B=0,t_p_B=0,t_c_B=0,s_customer_ID=0,t_customer_ID=0;
			
		
			//get previous balance
			try{
				System.out.println("Inside try1 of getting prev balance");
				Connection con=ConnectionProvider.getConnection();
				con.setAutoCommit(false);
	        	Statement st=con.createStatement();
				ResultSet resultset = st.executeQuery("select * from Account where Account_ID='" + obj.getAccount_ID() + "'");
				if(resultset.next()){
					s_p_B=resultset.getInt("Balance");
					s_customer_ID=resultset.getInt("Customer_ID");
					System.out.println("sourece previous balance "+s_p_B);
					con.close();
					if(s_p_B<obj.getAmount())
					{
						//display message insufficent fund
						System.out.println("insufficient fund");
						Map<String, String> data = new HashMap<>();
			  		      data.put("prev_B", Integer.toString(s_p_B));
			  		      data.put("cr_B", Integer.toString(s_c_B));
			  		      data.put("account_ID", Integer.toString(obj.getAccount_ID()));
			  		      data.put("customer_ID", Integer.toString(s_customer_ID));
			  		      data.put("err", "true");
			  		      data.put("message", "transfer not allowed please choose smaller amount");
			  		      String json = new Gson().toJson(data);
			  		      

			  		      response.setContentType("application/json");
			  		      response.setCharacterEncoding("UTF-8");
			  		      response.getWriter().write(json);
					}
					else
					{
						//get prev balance of target account
						Connection con1=ConnectionProvider.getConnection();
						con1.setAutoCommit(false);
			        	Statement st1=con1.createStatement();
						ResultSet resultset1 = st1.executeQuery("select * from Account where Account_ID='" + obj.getT_account_ID() + "'");
						if(resultset1.next()){
							t_p_B=resultset1.getInt("Balance");
							t_customer_ID=resultset1.getInt("Customer_ID");
							
						}
						s_c_B=s_p_B-obj.getAmount();
						t_c_B=t_p_B + obj.getAmount();
						con1.close();
						//update tables
						// second connection and start
						Connection con2=ConnectionProvider.getConnection();
						System.out.println("Inside try2 of inserting value in transaction table ");
						con2.setAutoCommit(false);
						//insert using prepared statements
						String query="INSERT INTO Transaction (ACCOUNT_ID, C_D, TR_DATE, Amount, description, CR_Balance)"
						+"VALUES(?,?,?,?,?,?)";
						// create the Oracle insert preparedstatement
					      PreparedStatement preparedStmt = con2.prepareStatement(query);
					     
					      preparedStmt.setInt (1, obj.getAccount_ID());
					    
					      preparedStmt.setString (2, "debit");
					      
					      preparedStmt.setTimestamp   (3, new Timestamp(System.currentTimeMillis()));
					      
					      preparedStmt.setInt(4, obj.getAmount());
					      
					      preparedStmt.setString    (5, "Transfer");
					      
					      preparedStmt.setInt    (6, s_c_B);
					      

					      // execute the preparedstatement
					      preparedStmt.execute();
					      con2.close();
					      //third connection
					      // add extra for target account transaction
					      Connection con3=ConnectionProvider.getConnection();
							System.out.println("Inside try2 of inserting value in transaction table ");
							con3.setAutoCommit(false);
							//insert using prepared statements
							String query3="INSERT INTO Transaction (ACCOUNT_ID, C_D, TR_DATE, Amount, description, CR_Balance)"
							+"VALUES(?,?,?,?,?,?)";
							// create the Oracle insert preparedstatement
						      PreparedStatement preparedStmt1 = con3.prepareStatement(query3);
						      System.out.println("Inside try2 of inserting value in transaction table ");
						      preparedStmt1.setInt (1, obj.getT_account_ID());
						      System.out.println("Inside try2 of inserting value in transaction table ");
						      preparedStmt1.setString (2, "credit");
						      System.out.println("Inside try2 of inserting value in transaction table ");
						      preparedStmt1.setTimestamp   (3, new Timestamp(System.currentTimeMillis()));
						      System.out.println("Inside try2 of inserting value in transaction table ");
						      preparedStmt1.setInt(4, obj.getAmount());
						      System.out.println("Inside try2 of inserting value in transaction table ");
						      preparedStmt1.setString    (5, "Transfer");
						      System.out.println("Inside try2 of inserting value in transaction table ");
						      preparedStmt1.setInt    (6, t_c_B);
						      System.out.println("Inside try2 of inserting value in transaction table ");

						      // execute the preparedstatement
						      preparedStmt1.execute();
						      con3.close();

						//fourth connection
					      Connection con4=ConnectionProvider.getConnection();
							
							//now update account table also
						      String query1="UPDATE Account SET Balance = ? WHERE Account_ID = ?";
								// create the Oracle insert preparedstatement
							      PreparedStatement preparedStmt4 = con4.prepareStatement(query1);
							    
							      preparedStmt4.setInt (1, s_c_B);
							      preparedStmt4.setInt   (2, obj.getAccount_ID());
							      // execute the preparedstatement
							      preparedStmt4.execute();
							      con4.close();
						//fifth connection for target account updation
							      Connection con5=ConnectionProvider.getConnection();
									System.out.println("Inside try3 of updating prev balance");
									//now update account table also
								      String query5="UPDATE Account SET Balance = ? WHERE Account_ID = ?";
										// create the Oracle insert preparedstatement
									      PreparedStatement preparedStmt5 = con5.prepareStatement(query5);
									    
									      preparedStmt5.setInt (1, t_c_B);
									      preparedStmt5.setInt   (2, obj.getT_account_ID());
									      // execute the preparedstatement
									      preparedStmt5.execute();
									      con5.close();
							      //now send json data
							      Map<String, String> data = new HashMap<>();
					  		      data.put("prev_B", Integer.toString(s_p_B));
					  		      data.put("cr_B", Integer.toString(s_c_B));
					  		      data.put("account_ID", Integer.toString(obj.getAccount_ID()));
					  		      data.put("customer_ID", Integer.toString(s_customer_ID));
					  		    data.put("t_prev_B", Integer.toString(t_p_B));
					  		      data.put("t_cr_B", Integer.toString(t_c_B));
					  		      data.put("t_account_ID", Integer.toString(obj.getT_account_ID()));
					  		      data.put("t_customer_ID", Integer.toString(t_customer_ID));
					  		      data.put("err", "false");
					  		      data.put("message", "amount Transfer is done successfully");
					  		      String json = new Gson().toJson(data);
					  		      

					  		      response.setContentType("application/json");
					  		      response.setCharacterEncoding("UTF-8");
					  		      response.getWriter().write(json);

					}
					
				}
				
				
				
			}
			catch(SQLException e){
				Map<String, String> data = new HashMap<>();
	  		     
				data.put("prev_B", Integer.toString(t_p_B));
	  		      data.put("cr_B", Integer.toString(t_c_B));
	  		      data.put("account_ID", Integer.toString(obj.getAccount_ID()));
	  		      data.put("customer_ID", Integer.toString(s_customer_ID));
	  		    data.put("t_prev_B", Integer.toString(t_p_B));
	  		      data.put("t_cr_B", Integer.toString(t_c_B));
	  		      data.put("t_account_ID", Integer.toString(obj.getT_account_ID()));
	  		      data.put("t_customer_ID", Integer.toString(t_customer_ID));
	  		      data.put("err", "true");
	  		      data.put("message", "amount does not transferred successfully Account ID does not exist");
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
