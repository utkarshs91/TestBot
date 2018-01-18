package framework;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetDBConnection {

	public static Connection getDBCOnnection() throws ClassNotFoundException, FileNotFoundException, SQLException
	{
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
		Connection conn = DriverManager.getConnection(BaseMethods.GetPropertiesFIleData().getProperty("UATDBURL"), BaseMethods.GetPropertiesFIleData().getProperty("DBUsername"), BaseMethods.GetPropertiesFIleData().getProperty("DBPassword"));
		System.out.println("Connection to Database established Successfully");
		
		return conn;	
	}
	
}
