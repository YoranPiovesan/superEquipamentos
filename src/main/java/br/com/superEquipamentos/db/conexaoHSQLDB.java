package br.com.superEquipamentos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaoHSQLDB {
	private static String usuario =  "SA";
	private static String senha = "";
	private static String pathBase = "C:\\Users\\Pichau\\eclipse-workspace\\superEquipamentos\\Dados\\superEquipamentos";
	private static String URL =  "jdbc:hsqldb:file:" + pathBase +";";
	
	public static Connection conectar() {
		try {
			return DriverManager.getConnection(URL, usuario, senha);
		} catch (SQLException e) {	
			e.printStackTrace();
			return null;
		}
	}
}
