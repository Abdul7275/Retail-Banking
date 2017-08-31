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
 * Servlet implementation class StatementByDate
 */
@WebServlet("/stmt_date")
public class StatementByDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatementByDate() {
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
		StatementQuery obj=new Gson().fromJson(request.getReader(), StatementQuery.class);
		System.out.println("Start Date" +obj.getStart_date());
		String sD=obj.getStart_date();
		String eD=obj.getEnd_date();
		String[] s=sD.split("-");
		String[] end=eD.split("-");
		System.out.println("Start date day :"+s[2]);
		System.out.println("Start date month :"+s[1]);
		System.out.println("Start date year :"+s[0]);
		
		String sday=s[2];
		String smonth=s[1];
		String syear=s[0];
		
		String eday=end[2];
		String emonth=end[1];
		String eyear=end[0];
		//convert according to oracle database month
		if(s[1].equals("04")){
			smonth="APR";
			System.out.println("Inside month updating");
			
		}
		else if(s[1].equals("05")){
			smonth="MAY";
			System.out.println("Inside month updating");
			
		}
		else if(s[1].equals("06")){
			smonth="JUNE";
			System.out.println("Inside month updating");
			
		}else if(s[1].equals("07")){
			smonth="JULY";
			System.out.println("Inside month updating");
			
		}else if(s[1].equals("08")){
			smonth="AUG";
			System.out.println("Inside month updating");
			
		}else if(s[1].equals("09")){
			smonth="SEPT";
			System.out.println("Inside month updating");
			
		}else if(s[1].equals("10")){
			smonth="OCT";
			System.out.println("Inside month updating");
			
		}
		if(end[1].equals("04")){
			emonth="APR";
			System.out.println("Inside month updating");
			
		}
		else if(end[1].equals("05")){
			emonth="MAY";
			System.out.println("Inside month updating");
			
		}
		// updating month has been done.
		//update last date day by one increment.
		int a=(Integer.parseInt(eday)+1);
		eday=Integer.toString(a);
		//updating last date has been done here.
		System.out.println("Start date day :"+sday);
		System.out.println("Start date month :"+smonth);
		System.out.println("Start date year :"+syear);
		System.out.println("end date day :"+eday);
		System.out.println("end date month :"+emonth);
		System.out.println("end date year :"+eyear);
		
		System.out.println("End Date" +obj.getEnd_date());
		Statement st;
		ResultSet resultset;
		Connection con1=ConnectionProvider.getConnection();
		
		
        try {
        	con1.setAutoCommit(false);
			st=con1.createStatement();
			resultset = st.executeQuery("select * from transaction where ACCOUNT_ID='"+obj.getAccount_ID()+"' AND tr_date between '"+sday+"-"+smonth+"-"+syear+"' AND '"+eday+"-"+emonth+"-"+eyear+" ' ");
			// converting resultset into json starts here
			List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
			int j=0;
			 while(resultset.next()){
				 int total_columns = resultset.getMetaData().getColumnCount();
				 Map<String, Object> data = new HashMap<>();
				 System.out.println("nomber of columns "+total_columns);
				 for (int i = 0; i < total_columns; i++) {
					   if(resultset.getMetaData().getColumnLabel(i + 1).toLowerCase().equals("tr_date")){
						   data.put(resultset.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultset.getTimestamp(i + 1));
					   }
					   else{
						   data.put(resultset.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultset.getObject(i + 1));
					   }
		                
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
	}

}
