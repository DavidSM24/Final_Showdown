package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Connection con;
	//Esto debe ir en un XML
	private final static String server="jdbc:mysql://localhost:3307";
	private final static String database="final_showdown";
	private final static String username="root";
	private final static String password="";
	
	public static void connectDB() {
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(server+"/"+database,username,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			con=null;
			e.printStackTrace();
		}
	}
	
	public static Connection getConexion() {
		if(con==null) {
			connectDB();
		}
		return con;
	}
}
