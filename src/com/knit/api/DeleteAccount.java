package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connect.ConnectionProvider;

/**
 * Servlet implementation class DeleteAccount
 */
@WebServlet("/deleteAcc")
public class DeleteAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccount() {
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
		// TODO Auto-generated method stub
				if (request.getSession().getAttribute("username") == null && request.getSession().getAttribute("role")=="executive") {
				    response.sendRedirect( "/"); // Not logged in, redirect to login page.
				    System.out.println("Not Logged In");
				}
				else {
				     // Logged in, just continue chain.
					System.out.println("Logged In");
				
				Account obj = new Gson().fromJson(request.getReader(), Account.class);
				System.out.println("Account_ID from json data in delete on server side is :"+obj.getAccount_ID());
				System.out.println("customer_ID from json in delete data on server side is :"+obj.getCustomer_ID());
				
				
				// now update this data in data starts here
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
				
				
		        
		        
					
				//new query
				
				
				

				
				try {
					Connection con=ConnectionProvider.getConnection();
					String query="";
					if(k==0){
						query="Update Account SET DELETE_FLAG= ? Where ACCOUNT_ID= ? AND DELETE_FLAG=?";
						PreparedStatement preparedStmt = con.prepareStatement(query);
					    
					      preparedStmt.setString (1, "Y");   
					      preparedStmt.setInt(2, Integer.parseInt(obj.getAccount_ID()));
					      preparedStmt.setString (3, "N");

					      // execute the preparedstatement
					      preparedStmt.execute();
					      
					      
					      con.close();

					}
					else{
		        	con.setAutoCommit(false);
		        	//insert using prepared statements
					 query="UPDATE ACCOUNT SET DELETE_FLAG = ? WHERE Customer_ID = ? AND ACCOUNT_TYPE=? AND DELETE_FLAG=?";
					// create the Oracle insert preparedstatement
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				    
				      preparedStmt.setString (1, "Y");   
				      preparedStmt.setInt(2, Integer.parseInt(obj.getCustomer_ID()));
				      preparedStmt.setString(3, obj.getAccount_Type());
				      preparedStmt.setString (4, "N");

				      // execute the preparedstatement
				      preparedStmt.execute();
				      
				      
				      con.close();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// updating data in database has ended here
			

				
				
			}//ends else statement
				
	}

}
