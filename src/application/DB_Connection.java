package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
	public static Connection connection = null;
	
	public static void open() {
		try {
			String host = "jdbc:mysql://localhost:3306/inventory_management_system";
			connection = DriverManager.getConnection(host, "Frank", "010601Succes$");
			System.out.println("connected");
		} catch (SQLException e) {
			e.getMessage();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public static void close() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
}
