package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

private static final String jdbcURL = "jdbc:mysql://localhost/metroparis?user=root";
	
	public static Connection getConnection(){
		Connection conn;
		try{
			conn = DriverManager.getConnection(jdbcURL);
			return conn;
		} catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException("Errore nella connessione", e);
		}
	}
	
}