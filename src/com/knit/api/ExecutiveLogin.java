 package com.knit.api;
 import connect.ConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class CashierLogin
 */
@WebServlet("/c_login")
public class ExecutiveLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecutiveLogin() {
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
		// read form fields
		//FetchUpdateQuery obj = new Gson().fromJson(request.getReader(), FetchUpdateQuery.class);
		//Login obj = new Gson().fromJson(request.getReader(), Login.class);
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				System.out.println("username: " + username);
				System.out.println("password: " + password);

				// do some processing here...
				Statement st;
				ResultSet resultset;
				Connection con=ConnectionProvider.getConnection();
				
				
	            try {
	            	con.setAutoCommit(false);
					st=con.createStatement();
					String message="";
					resultset = st.executeQuery("select * from executive where username='" + username + "' AND password='"+password+"'");
		            if(resultset.next())
		            {
		            	message="user Logged successfully";
		            	HttpSession session=request.getSession();  
		                session.setAttribute("username",username);
		                System.out.println("logged in");
		                session.setAttribute("role","executive");
		                request.setAttribute("message", message);
		                
		            	getServletContext().getRequestDispatcher("/executiveHome2.html").forward(request, response);
		            }
		            else{
		            	message="Invalid username password!";
		            	request.setAttribute("message", message);
		            	getServletContext().getRequestDispatcher("/").forward(request, response);
		            }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            

		
		
		
	}

}
