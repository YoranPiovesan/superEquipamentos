package br.com.superEquipamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.superEquipamentos.db.conexaoHSQLDB;
import br.com.superEquipamentos.model.CaixaCambio;
import br.com.superEquipamentos.model.Tributo;

public class caixaCambioDAO  extends conexaoHSQLDB implements Tributo {
	
	final static String SQL_INSERT_CAIXACAMBIO = "INSERT INTO CAIXACAMBIO(NOME, FABRICANTE, CUSTO, TIPO, DESCRICAO) VALUES (?,?,?,?,?)";
	final static String SQL_SELECT_CAIXACAMBIO = "SELECT * FROM CAIXACAMBIO";
	final static String SQL_SELECT_CAIXACAMBIO_ID = "SELECT * FROM CAIXACAMBIO WHERE ID = ?";
	final static String SQL_ALTERA_CAIXACAMBIO = "UPDATE CAIXACAMBIO SET NOME=?, FABRICANTE=?, CUSTO=?, TIPO=?, DESCRICAO=? WHERE ID = ?";
	final static String SQL_DELETA_CAIXACAMBIO = "DELETE FROM CAIXACAMBIO WHERE ID = ?";
	
	public double tributoEquipamento(double custo) {
		double custoFinal = custo * 0.10;
		return custoFinal + custo;
	}
	public boolean inserir(CaixaCambio caixaCambio) {
		if(caixaCambio == null) {
			return false;
		}
		if(caixaCambio.getNome() == null || caixaCambio.getNome().equals("")) {
			return false;
		}
		if(caixaCambio.getFabricante() == null || caixaCambio.getFabricante().equals("")) {
			return false;
		}
		if(caixaCambio.getTipo() == null || caixaCambio.getTipo().equals("")) {
			return false;
		}
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_INSERT_CAIXACAMBIO);) {
			pst.setString(1, caixaCambio.getNome());
			pst.setString(2, caixaCambio.getFabricante());
			pst.setDouble(3, tributoEquipamento(caixaCambio.getCusto()));
			pst.setString(4, caixaCambio.getTipo());
			pst.setString(5, caixaCambio.getDescricao());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static List<CaixaCambio> listAll() {
		List<CaixaCambio> listaCaixaCambio = new ArrayList<CaixaCambio>();
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_CAIXACAMBIO);) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CaixaCambio caixaCambio = new CaixaCambio();
				caixaCambio.setId(rs.getInt("ID"));
				caixaCambio.setNome(rs.getString("NOME"));
				caixaCambio.setFabricante(rs.getString("FABRICANTE"));
				caixaCambio.setCusto(rs.getFloat("CUSTO"));
				caixaCambio.setTipo(rs.getString("TIPO"));
				caixaCambio.setDescricao(rs.getString("DESCRICAO"));
				listaCaixaCambio.add(caixaCambio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaCaixaCambio;
	}
	public CaixaCambio findByID(int id) {
		CaixaCambio caixaCambio = null;
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_CAIXACAMBIO_ID);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				caixaCambio = new CaixaCambio();
				caixaCambio.setId(rs.getInt("ID"));
				caixaCambio.setNome(rs.getString("NOME"));
				caixaCambio.setFabricante(rs.getString("FABRICANTE"));
				caixaCambio.setCusto(rs.getFloat("CUSTO"));
				caixaCambio.setTipo(rs.getString("TIPO"));
				caixaCambio.setDescricao(rs.getString("DESCRICAO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return caixaCambio;
	}
	public boolean alterar(CaixaCambio caixaCambio) {
		if(caixaCambio == null) {
			return false;
		}
		if(caixaCambio.getNome() == null || caixaCambio.getNome().equals("")) {
			return false;
		}
		if(caixaCambio.getFabricante() == null || caixaCambio.getFabricante().equals("")) {
			return false;
		}
		if(caixaCambio.getTipo() == null || caixaCambio.getTipo().equals("")) {
			return false;
		}
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_ALTERA_CAIXACAMBIO);
				) {
			pst.setString(1, caixaCambio.getNome());
			pst.setString(2, caixaCambio.getFabricante());
			pst.setDouble(3, caixaCambio.getCusto());
			pst.setString(4, caixaCambio.getTipo());
			pst.setString(5, caixaCambio.getDescricao());
			pst.setInt(6, caixaCambio.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean deletar(int id) {
		try (Connection connection = conectar();
				PreparedStatement pst = connection.prepareStatement(SQL_DELETA_CAIXACAMBIO);) {
			pst.setInt(1, id);
            pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

}
