package com.knit.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connect.ConnectionProvider;

/**
 * Servlet implementation class JsonResultSetTesting
 */
@WebServlet("/json-test")
public class JsonResultSetTesting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonResultSetTesting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Statement st;
		ResultSet resultset;
		Connection con1=ConnectionProvider.getConnection();
		
		
        try {
        	con1.setAutoCommit(false);
			st=con1.createStatement();
			resultset = st.executeQuery("select * from Customer ");
			// converting resultset into json starts here
			List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
			int j=0;
			 while(resultset.next()){
				 int total_columns = resultset.getMetaData().getColumnCount();
				 Map<String, Object> data = new HashMap<>();
				 System.out.println("nomber of columns "+total_columns);
				 for (int i = 0; i < total_columns; i++) {
		                data.put(resultset.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultset.getObject(i + 1));
		            }
				 listOfMaps.add(j,data);
				 j++;
			 }
           
  		      String json = new Gson().toJson(listOfMaps);
  		 // converting resultset into json ends here
  		      System.out.println(json);

  		      response.setContentType("application/json");
  		      response.setCharacterEncoding("UTF-8");
  		      response.getWriter().write(json);
            }
            catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now retrive customer id from database and send it to client ends here.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
