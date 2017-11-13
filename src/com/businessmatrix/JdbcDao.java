package com.businessmatrix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDao {

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	
	public static Connection getConnection() throws SQLException{
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:hive2://10.81.48.43:10000", "hive", "123456");
	}
	
	public static void close(Connection con ,Statement sm,ResultSet rs) throws SQLException{
		if(con!=null)con.close();
		if(sm!=null)sm.close();
		if(rs!=null)rs.close();
	}
}
