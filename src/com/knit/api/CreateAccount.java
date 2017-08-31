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
 * Servlet implementation class CreateAccount
 */
@WebServlet("/createAcc")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("username") == null && request.getSession().getAttribute("role")=="executive") {
		    response.sendRedirect( "/"); // Not logged in, redirect to login page.
		    System.out.println("Not Logged In");
		}
		else {
		     // Logged in, just continue chain.
			System.out.println("Logged In");
		
		CAccount obj = new Gson().fromJson(request.getReader(), CAccount.class);
		//System.out.println("Customer_ID from json data on server side is :"+obj.getCustomer_ID());
		//System.out.println("Acc type from json data on server side is :"+obj.getAcc_type());
		//System.out.println("Balance from json data on server side is :"+obj.getBalance());
//check for already existing account
		try{
			Connection con=ConnectionProvider.getConnection();
			
        	con.setAutoCommit(false);
        	Statement st=con.createStatement();
			ResultSet resultset = st.executeQuery("select * from Account where Customer_ID='" + obj.getCustomer_ID() + "' AND Account_Type='" +obj.getAcc_type()+"'");
			if(resultset.next()){
				Map<String, String> data = new HashMap<>();
            	data.put("message", "Account already exists ");
            	String json = new Gson().toJson(data);
            	data.put("err", "true");

	  		      response.setContentType("application/json");
	  		      response.setCharacterEncoding("UTF-8");
			}
			// do all other stuff under this else
			else{
				// now store this data in data starts here
				
				try {
					 con=ConnectionProvider.getConnection();
					
		        	con.setAutoCommit(false);
		        	//insert using prepared statements
					String query="INSERT INTO Account (Customer_ID, Account_Type, Balance, CR_DATE, DELETE_FLAG)"
					+"VALUES(?,?,?,?,?)";
					// create the Oracle insert preparedstatement
					
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      System.out.println("Customer_ID inside insert :"+obj.getCustomer_ID());
				      preparedStmt.setInt (1, Integer.parseInt(obj.getCustomer_ID()));
				      System.out.println("Account Type inside insert :"+obj.getAcc_type());
				      preparedStmt.setString (2, obj.getAcc_type());
				      System.out.println("Balance inside insert :"+obj.getBalance());
				      preparedStmt.setInt   (3, obj.getBalance());
				      System.out.println("Timestamp inside insert :"+new Timestamp(System.currentTimeMillis()));
				      preparedStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

				      preparedStmt.setString    (5, "N");

				      // execute the preparedstatement
				      preparedStmt.execute();
				      
				      
				      con.close();
				   // now retrive customer id from database and send it to client starts here.
						//now get Customer_ID from database and send back to client in json form.
						
						
						
				        try {
				        	Statement st1;
				    		ResultSet resultset1;
				    		
				    		Connection con1=ConnectionProvider.getConnection();
				    		
				        	con1.setAutoCommit(false);
				        	System.out.println("inside retriving data");
							st1=con1.createStatement();
							//CAccount obj2 = new Gson().fromJson(request.getReader(), CAccount.class);
							System.out.println("inside retriving data");
							System.out.println("Customer_ID inside retrive :"+obj.getCustomer_ID());
							System.out.println("inside retriving data");
							resultset1 = st1.executeQuery("SELECT * from ACCOUNT where Customer_ID='" + Integer.parseInt(obj.getCustomer_ID()) + "'AND Account_Type='"+obj.getAcc_type()+"'");
							 
				            if(resultset1.next())
				            {
				              int A_ID=resultset1.getInt("ACCOUNT_ID");
				  		      System.out.println("ACCOUNT Id for Customer Id  "+obj.getCustomer_ID()+" is :"+A_ID);
				  		    Map<String, String> data = new HashMap<>();
				  		      data.put("Account_ID", Integer.toString(A_ID));
				  		      data.put("message", "Account created successfully");
				  		    data.put("err", "false");
				  		      String json = new Gson().toJson(data);
				  		      

				  		      response.setContentType("application/json");
				  		      response.setCharacterEncoding("UTF-8");
				  		      response.getWriter().write(json);
				            }
				            else{
				            	Map<String, String> data = new HashMap<>();
				            	data.put("message", "Account is not created successfully");
				            	String json = new Gson().toJson(data);
				            	data.put("err", "true");

					  		      response.setContentType("application/json");
					  		      response.setCharacterEncoding("UTF-8");
				            }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							Map<String, String> data = new HashMap<>();
							e.printStackTrace();
							data.put("err", "true");
							String json = new Gson().toJson(data);
				  		      

				  		      response.setContentType("application/json");
				  		      response.setCharacterEncoding("UTF-8");
						}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Map<String, String> data = new HashMap<>();
					e.printStackTrace();
					data.put("err", "true");
					String json = new Gson().toJson(data);
		  		      

		  		      response.setContentType("application/json");
		  		      response.setCharacterEncoding("UTF-8");
					
				}
			}
		}
		catch (SQLException e){
			Map<String, String> data = new HashMap<>();
			e.printStackTrace();
			data.put("err", "true");
			data.put("message", "SQL Error on server");
			String json = new Gson().toJson(data);
  		      

  		      response.setContentType("application/json");
  		      response.setCharacterEncoding("UTF-8");
		}
		
		
			// storing data in database has ended here
			
			

			// now retrive customer id from database and send it to client ends here.
		
		}//else ending
		
		

		

		

		
		
	}
	}


