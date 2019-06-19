package vasic.web.programiranje.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vasic.web.programiranje.dto.LetDTO;
import vasic.web.programiranje.model.Let;
import vasic.web.programiranje.tools.ConnectionManager;

public class LetDAO {
	
	public static List<Let> getAll(){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM let WHERE izbrisan=0";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return letovi;
		
	} 
	
	
	public static Let getById(int id){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		try {
			String query = "SELECT * FROM let WHERE id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				let.setIzbrisan(resultSet.getBoolean("izbrisan"));
				
				return let;
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	} 
	
	
	
	public static List<Let> getAllByBroj(String broj){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM let WHERE broj like ? AND izbrisan=0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, broj+'%');
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return letovi;
		
	} 
	
	
	public static List<Let> povratniLetovi(Let let1){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM let WHERE cena polazni_aerodrom_id= ? AND datum_polska > ? AND izbrisan = 0";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, let1.getDolazniAerodrom().getId());
			java.sql.Timestamp sqdatumPolaska = new java.sql.Timestamp(let1.getVremeDolaska().getTime());
			preparedStatement.setTimestamp(2, sqdatumPolaska);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return letovi;
	}
	
	public static List<Let> getAllByCena(float cenaOd, float cenaDo){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM let WHERE cena BETWEEN ? AND ? AND izbrisan = 0";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setFloat(1, cenaOd);
			preparedStatement.setFloat(2, cenaDo);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return letovi;
		
	} 
	
	
	public static List<Let> getAllByDatum(String tip,Date datuomOd, Date datumDo){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		
		try {
			String query="";
			if(tip.equals("polazak")) {
				 query += "SELECT * FROM let WHERE izbrisan = 0 AND datum_polaska BETWEEN ? AND ?";
			} else {
				 query += "SELECT * FROM let WHERE izbrisan = 0 AND datum_dolaska BETWEEN ? AND ?";

			}
			
			preparedStatement = connection.prepareStatement(query);
			java.sql.Timestamp sqlDatumOd = new java.sql.Timestamp(datuomOd.getTime());
			java.sql.Timestamp sqlDatumDo = new java.sql.Timestamp(datumDo.getTime());
			preparedStatement.setTimestamp(1, sqlDatumOd);
			preparedStatement.setTimestamp(2, sqlDatumDo);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return letovi;
		
	} 

	public static List<Let> getAllByAerodrom(String type, int aerodromId){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		
		try {
			
			String query="";
			if(type.equalsIgnoreCase("polazni")) {
				query += "SELECT a.* FROM let AS a LEFT JOIN aerodrom AS b ON a.polazni_aerodrom_id = b.naziv WHERE a.izbrisan = 0 AND a.polazni_aerodrom_id = ?;";
			} else {
				query += "SELECT a.* FROM let AS a LEFT JOIN aerodrom AS b ON a.dolazni_aerodrom_id = b.naziv WHERE a.izbrisan = 0 AND a.dolazni_aerodrom_id= ?;";
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, aerodromId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return letovi;
		
	} 
	
	
	
	public static boolean dodajLet(Let let) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "INSERT INTO let (broj, datum_polaska, datum_dolaska, polazni_aerodrom_id,dolazni_aerodrom_id, broj_sedista, cena,izbrisan) values (?,?,?,?,?,?,?,0);";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, let.getBroj());
			java.sql.Timestamp sqdatumPolaska = new java.sql.Timestamp(let.getVremePolaska().getTime());
			java.sql.Timestamp sqdatumDolaska = new java.sql.Timestamp(let.getVremeDolaska().getTime());
			preparedStatement.setTimestamp(2, sqdatumPolaska);
			preparedStatement.setTimestamp(3, sqdatumDolaska);
			preparedStatement.setInt(4, let.getPolazniAerodrom().getId());
			preparedStatement.setInt(5, let.getDolazniAerodrom().getId());
			preparedStatement.setInt(6, let.getBrojSedista());
			preparedStatement.setFloat(7, let.getCena());
			
			return preparedStatement.executeUpdate() == 1;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		
		return false;
		
	}
	
	
	public static boolean azurirajLet(LetDTO let) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		
		try {
			
			String query = "UPDATE let SET datum_polaska = ?, datum_dolaska=?,broj_sedista=?,cena=? WHERE id =? AND izbrisan = 0;";
			preparedStatement = connection.prepareStatement(query);
			java.sql.Timestamp sqdatumPolaska = new java.sql.Timestamp(let.getVremePolaska().getTime());
			java.sql.Timestamp sqdatumDolaska = new java.sql.Timestamp(let.getVremeDolaska().getTime());
			preparedStatement.setTimestamp(1, sqdatumPolaska);
			preparedStatement.setTimestamp(2, sqdatumDolaska);
			preparedStatement.setInt(3, let.getBrojSedista());
			preparedStatement.setFloat(4, let.getCena());
			preparedStatement.setInt(5, let.getId());
			
			
			return preparedStatement.executeUpdate() == 1;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		
		return false;
		
	}
	
	public static boolean izbrisiLet(int id) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM let WHERE id = ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,id);
			
			return preparedStatement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {preparedStatement.close();} catch (SQLException ex) {ex.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean logicalDelte(int id) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE let SET izbrisan = 1 WHERE id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,id);
			
			return preparedStatement.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {preparedStatement.close();} catch (SQLException ex) {ex.printStackTrace();}
		}
		
		return false;
	}
	
	
	public static List<Let> dostupniLetovi(){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		try {
			String query = "SELECT * FROM let WHERE datum_polaska >= now() AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let = new Let();
				let.setId(resultSet.getInt("id"));
				let.setBroj(resultSet.getString("broj"));
				let.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let.setBrojSedista(resultSet.getInt("broj_sedista"));
				let.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let);
				
			}			
		}
	 catch (Exception e) {
		System.out.println(e.getMessage());
	} finally {
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	return letovi;
	
	}
	
	public static boolean exist(int id) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			String query = "SELECT COUNT(id) from let WHERE id=? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {preparedStatement.close();} catch (SQLException ex) {ex.printStackTrace();}
		}
		return false;
	}
	
	
	public static List<Let> dostupniPovratniLetvi(Let let){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Let> letovi = new ArrayList<>();
		try {
			String query = "SELECT * FROM let WHERE polazni_aerodrom_id = ? AND dolazni_aerodrom_id = ? AND datum_polaska > ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, let.getDolazniAerodrom().getId());
			preparedStatement.setInt(2, let.getPolazniAerodrom().getId());
			java.sql.Timestamp sqdatumDolaska = new java.sql.Timestamp(let.getVremeDolaska().getTime());
			preparedStatement.setTimestamp(3, sqdatumDolaska);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Let let1 = new Let();
				let1.setId(resultSet.getInt("id"));
				let1.setBroj(resultSet.getString("broj"));
				let1.setVremePolaska(resultSet.getTimestamp("datum_polaska"));
				let1.setVremeDolaska(resultSet.getTimestamp("datum_dolaska"));
				let1.setPolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("polazni_aerodrom_id")));
				let1.setDolazniAerodrom(AerodromDAO.getOne(resultSet.getInt("dolazni_aerodrom_id")));
				let1.setBrojSedista(resultSet.getInt("broj_sedista"));
				let1.setCena(resultSet.getFloat("cena"));
				
				letovi.add(let1);
			}

		}
		
	catch (Exception e) {
		System.out.println(e.getMessage());
	} finally {
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	return letovi;
	}

}
