package br.com.superEquipamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.superEquipamentos.db.conexaoHSQLDB;
import br.com.superEquipamentos.model.Motor;
import br.com.superEquipamentos.model.Tributo;

public class motorDAO  extends conexaoHSQLDB implements Tributo {

	final static String SQL_INSERT_MOTOR = "INSERT INTO MOTOR(NOME, FABRICANTE, CUSTO, VELOCIDADE, POTENCIA) VALUES (?,?,?,?,?)";
	final static String SQL_SELECT_MOTOR = "SELECT * FROM MOTOR";
	final static String SQL_SELECT_MOTOR_ID = "SELECT * FROM MOTOR WHERE ID = ?";
	final static String SQL_ALTERA_MOTOR = "UPDATE MOTOR SET NOME=?, FABRICANTE=?, CUSTO=?, VELOCIDADE=?, POTENCIA=? WHERE ID = ?";
	final static String SQL_DELETA_MOTOR = "DELETE FROM MOTOR WHERE ID = ?";
	
	public double tributoEquipamento(double custo) {
		double custoFinal = custo * 0.20;
		return custoFinal + custo;
	}
	public boolean inserir(Motor motor) {
		if(motor == null) {
			return false;
		}
		if(motor.getNome() == null || motor.getNome().equals("")) {
			return false;
		}
		if(motor.getFabricante() == null || motor.getFabricante().equals("")) {
			return false;
		}
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_INSERT_MOTOR);) {
			pst.setString(1, motor.getNome());
			pst.setString(2, motor.getFabricante());
			pst.setDouble(3, tributoEquipamento(motor.getCusto()));
			pst.setLong(4, motor.getVelocidade());
			pst.setInt(5, motor.getPotencia());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public List<Motor> listAll() {
		List<Motor> listaMotor = new ArrayList<Motor>();
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_MOTOR);) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Motor motor = new Motor();
				motor.setId(rs.getInt("ID"));
				motor.setNome(rs.getString("NOME"));
				motor.setFabricante(rs.getString("FABRICANTE"));
				motor.setCusto(rs.getFloat("CUSTO"));
				motor.setVelocidade(rs.getLong("VELOCIDADE"));
				motor.setPotencia(rs.getInt("POTENCIA"));
				listaMotor.add(motor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaMotor;
	}
	public Motor findByID(int id) {
		Motor motor = null;
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_MOTOR_ID);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				motor = new Motor();
				motor.setId(rs.getInt("ID"));
				motor.setNome(rs.getString("NOME"));
				motor.setFabricante(rs.getString("FABRICANTE"));
				motor.setCusto(rs.getFloat("CUSTO"));
				motor.setVelocidade(rs.getLong("VELOCIDADE"));
				motor.setPotencia(rs.getInt("POTENCIA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return motor;
	}
	public boolean alterar(Motor motor) {
		if(motor == null) {
			return false;
		}
		if(motor.getNome() == null || motor.getNome().equals("")) {
			return false;
		}
		if(motor.getFabricante() == null || motor.getFabricante().equals("")) {
			return false;
		}
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_ALTERA_MOTOR);) {
			pst.setString(1, motor.getNome());
			pst.setString(2, motor.getFabricante());
			pst.setDouble(3, motor.getCusto());
			pst.setLong(4, motor.getVelocidade());
			pst.setInt(5, motor.getPotencia());
			pst.setInt(6, motor.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Error("Erro ao alterar Motor");
		}
		return true;
	}
	
	public boolean deletar(int id) {
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_DELETA_MOTOR);) {
			pst.setInt(1, id);
            pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	
	
}
