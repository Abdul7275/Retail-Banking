package connect;
//import java.io.FileInputStream;for FileInputStream
import java.sql.*;
//import java.util.Properties; for FileInputStream
public class ConnectionProvider {
	private static Connection con;
	private static String user="RBank";
	private static String password="RBank";
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@localhost:1521:xe";
  //.............................reading database credentials from db.properties
	/*public static void load(String path)
	{
		
		try
		{
		path=path+"\\"+"db.properties";
		FileInputStream fin=new FileInputStream(path);
		Properties p=new Properties();
		p.load(fin);
		user=p.getProperty("user");
		password=p.getProperty("password");
		driver=p.getProperty("driver");
		url=p.getProperty("url");
		System.out.println("user :"+user+" password :"+password);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("user :"+user+" password :"+password);
		}
	}*/
	
  //	..........................getting connetction using credentials..............................
	public static Connection getConnection()
	{
		try
		{

		if(con == null)
		{
			
	Class.forName(driver);
	Connection con=DriverManager.getConnection(url,user,password);

	System.out.println("Connecitn ..........."+con);
			return con; //return newly created connection
		}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		// return that connection which was already present
		return con;
	}
	//
	
	//
}
