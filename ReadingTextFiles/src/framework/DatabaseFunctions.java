package framework;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseFunctions {

	
	
	//Method to get the StatusID which will act as Unique identifier
	public static String SelectQuery (String columnName, String TableName, String SpecialCondition) throws ClassNotFoundException, SQLException, IOException
	{
			Connection conn = GetDBConnection.getDBCOnnection();
			Statement stm = conn.createStatement();
			
			String Sql = "select " +columnName + " from [" +BaseMethods.GetPropertiesFIleData().getProperty("DBName")+"].[dbo]." +TableName +" where " +SpecialCondition;
			
			ResultSet rs = stm.executeQuery(Sql);
			
			 while (rs.next()) 
			{
			  String QueryResult = rs.getString(columnName);
			  
			  return QueryResult;
			}
			
			 conn.close();
			 
			return Sql;

	}
	
	public static void UpdateQuery (String UpdateQuery) throws ClassNotFoundException, SQLException, IOException
	{
		Connection conn = GetDBConnection.getDBCOnnection();
		Statement stm = conn.createStatement();
		
			stm.executeUpdate(UpdateQuery);
			
			System.out.println("Update Query Run Successful");
			
			conn.close();
			
			System.out.println("Connection closed successfully");
	}
	

	
}
