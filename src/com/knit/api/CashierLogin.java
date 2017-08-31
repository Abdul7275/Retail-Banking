package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connect.ConnectionProvider;

/**
 * Servlet implementation class CashierLogin
 */
@WebServlet("/cash_login")
public class CashierLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CashierLogin() {
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
			resultset = st.executeQuery("select * from cashier where username='" + username + "'AND password='"+password+"'");
            if(resultset.next())
            {
            	message="logged in successfully";
            	HttpSession session=request.getSession();  
                session.setAttribute("username",username);
                session.setAttribute("role","cashier");
            	getServletContext().getRequestDispatcher("/cashierHome.html").forward(request, response);
            }
            else{
            	message="Invalid username password! ";
            	request.setAttribute("message", message);
            	getServletContext().getRequestDispatcher("/").forward(request, response);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
