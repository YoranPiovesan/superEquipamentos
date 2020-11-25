package br.com.superEquipamentos.testeDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.superEquipamentos.db.conexaoHSQLDB;



public class TesteConnection {

	public static void main(String[] args) {
		conexaoHSQLDB conn =  new conexaoHSQLDB();
		Connection connection =  conn.conectar();
		System.out.println(connection);
	}
}
