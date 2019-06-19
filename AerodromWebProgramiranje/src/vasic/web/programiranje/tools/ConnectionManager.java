package vasic.web.programiranje.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";	

	private static Connection connection;

	public static void open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webp_vasic", USER_NAME, PASSWORD);
		} catch (Exception ex) {
			System.out.println("Error " + ex);
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void close() {
		try {
			connection.close();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
