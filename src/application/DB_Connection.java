package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB_Connection {
	private Connection connection = null;
	
	public DB_Connection(){
		try {
			String configFilePath = "config.properties";
			FileInputStream propsInput = new FileInputStream(configFilePath);
			Properties prop = new Properties();
			prop.load(propsInput);
			System.out.println(prop.getProperty("DB_NAME"));
			String host = "jdbc:mysql://localhost:3306/"+"inventory_management_system";
			connection = DriverManager.getConnection(host, "Frank", "010601Succes$");
			System.out.println("connected");
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public void open() {
		String configFilePath = "config.properties";
		try {
			FileInputStream propsInput = new FileInputStream(configFilePath);
			Properties prop = new Properties();
			prop.load(propsInput);
			System.out.println(prop.getProperty("DB_NAME"));
			String host = "jdbc:mysql://localhost:3306/"+"inventory_management_system";
			connection = DriverManager.getConnection(host, "Frank", "010601Succes$");
			System.out.println("connected");
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public void close() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
}
