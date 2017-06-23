package com.example.addbook.tool;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;
/**这个类用来连接数据库*/
public class DBHelper {
	private static Connection connection;
	private static Statement statement;
	
	public static Statement getStatement() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://192.168.211.3:3306/backup", "root", "root");
			statement = connection.createStatement();
			return statement;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**用来执行增，改，删sql语句*/
	public static boolean excuteSql(String sql) {
		try {
			boolean isExcute = getStatement().execute(sql);
			return isExcute;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
}
