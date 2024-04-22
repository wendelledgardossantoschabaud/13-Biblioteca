package es.rodal.biblioteca.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	 private final static String url = "jdbc:mysql://localhost:3306/biblioteca";
	 private final static String username = "root";
	 private final static String password = "root";

	 public static Connection conectar() {
		 Connection conexion = null;
		// System.out.println("Loading driver ...");

		 try {
		     Class.forName("com.mysql.cj.jdbc.Driver");
		//    System.out.println("Driver loaded!");
		 } catch (ClassNotFoundException e) {
		     throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		 }
		 
		// System.out.println("Connecting database ...");
	
		 try  {
			 conexion = DriverManager.getConnection(url, username, password);
		//	 System.out.println("Database connected!");
		 } catch (SQLException e) {
		     throw new IllegalStateException("Cannot connect the database!", e);
		 }
		 
		 return conexion;
	 }
}
