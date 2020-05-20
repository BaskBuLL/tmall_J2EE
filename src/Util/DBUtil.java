package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
   static String ip="127.0.0.1";
   static int port = 3306;
   static String database = "tmall";
   static String encoding = "utf-8";
   static String loginName = "root";
   static String password = "password";
   
   static {
	   try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public static Connection getConnection() throws SQLException {
	   String url=String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s&useSSL=false", ip,port,database,encoding);
	   return DriverManager.getConnection(url,loginName,password);
   }
   
	public static void release(Statement stmt,Connection conn) {
		if(stmt!=null) {
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}stmt=null;
		}
		if(conn!=null) {
	   try {
		conn.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	}conn=null;
		}
	}
	
	public static void release(ResultSet rs,Statement stmt,Connection conn) {
		if(rs!=null) {
		try {
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
		//巧妙
		release(stmt,conn);
	}
   
   
}
