package vasic.web.programiranje.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.tools.ConnectionManager;

public class KorisnikDAO {
	
	public static boolean register(Korisnik korisnik) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "INSERT INTO korisnik (korisnicko_ime,lozinka,datum_registracije,admin,blokiran,izbrisan) VALUES (?,?,?,?,?,0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, korisnik.getKorisnickoIme());
			preparedStatement.setString(2, korisnik.getLozinka());
			java.sql.Timestamp sqlDatumRegistracije = new java.sql.Timestamp(korisnik.getDatumRegistracije().getTime());
			preparedStatement.setTimestamp(3, sqlDatumRegistracije);
			preparedStatement.setBoolean(4, korisnik.isAdmin());
			preparedStatement.setBoolean(5, korisnik.isBlokiran());
			
			return preparedStatement.executeUpdate() == 1;
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		
		return false;
	}
	
	public static Korisnik login(String username, String password) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		Korisnik korisnik = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT * FROM korisnik WHERE korisnicko_ime = ? AND lozinka = ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				korisnik = new Korisnik();
				korisnik.setId(resultSet.getInt("id"));
				korisnik.setKorisnickoIme(resultSet.getString("korisnicko_ime"));
				korisnik.setLozinka(resultSet.getString("lozinka"));
				korisnik.setDatumRegistracije(resultSet.getTimestamp("datum_registracije"));
				korisnik.setAdmin(resultSet.getBoolean("admin"));
				korisnik.setBlokiran(resultSet.getBoolean("blokiran"));
				return korisnik;
				
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return korisnik;
	}
	
	public static boolean logicnkiObrisi(String username) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			
			String query = "UPDATE korisnik set izbirsan = 1 WHERE korisnicko_ime = ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean changeRole(String username,String action) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "";
			if(action.equals("promote")) {
				query+="UPDATE korisnik set admin = 1 WHERE korisnicko_ime = ? AND admin = 0 AND izbrisan = 0";
			} else {
				query += "UPDATE korisnik set admin = 0 WHERE korisnicko_ime = ? AND admin = 1 AND izbrisan = 0";
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean izbrisiKorisnika(String username) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			
			String query = "DELETE FROM korisnik WHERE username = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	

	public static boolean blockAdmin(String username) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			
			String query = "UPDATE korisnik set blokiran = 1, admin = 0 WHERE korisnicko_ime = ? AND blokiran = 0 AND izbrisan = 0 AND admin = 1";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean blocking(String username,String action) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "";
			if(action.equals("block")) {
				 query += "UPDATE korisnik set blokiran = 1 WHERE korisnicko_ime = ? AND blokiran = 0 AND izbrisan = 0 AND admin = 0";

			} else {
				 query += "UPDATE korisnik set blokiran = 0 WHERE korisnicko_ime = ? AND blokiran = 1 AND izbrisan = 0 AND admin = 0";
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean changeMyPassword(String username, String oldPassword, String newPassword) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		
	try {
			
			String query = "UPDATE korisnik set lozinka = ? WHERE korisnicko_ime = ? AND lozinka = ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, oldPassword);
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static boolean changePassword(String username, String newPassword) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		
	try {
			
			String query = "UPDATE korisnik set lozinka = ? WHERE korisnicko_ime = ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, username);
	
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	
	public static boolean changeUsername(String oldUsername, String newUsername) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		System.out.println(oldUsername + " kita  " + newUsername);
		
	try {
			
			String query = "UPDATE korisnik set korisnicko_ime = ? WHERE korisnicko_ime = ? AND izbrisan = 0";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newUsername);
			preparedStatement.setString(2, oldUsername);

		
			
			
			return preparedStatement.executeUpdate() == 1;
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
	
	public static List<Korisnik> getAll(){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Korisnik> korisnici = new ArrayList<Korisnik>();

		try {
			String sql = "SELECT * FROM korisnik WHERE izbrisan = 0";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Korisnik korisnik = new Korisnik();
				korisnik.setId(resultSet.getInt("id"));
				korisnik.setKorisnickoIme(resultSet.getString("korisnicko_ime"));
				korisnik.setLozinka(resultSet.getString("lozinka"));
				korisnik.setDatumRegistracije(resultSet.getTimestamp("datum_registracije"));
				korisnik.setAdmin(resultSet.getBoolean("admin"));
				korisnik.setBlokiran(resultSet.getBoolean("blokiran"));
				korisnici.add(korisnik);
				
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return korisnici;
	}
	
	
	public static List<Korisnik> getAllByRole(boolean isAdmin){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Korisnik> korisnici = new ArrayList<Korisnik>();

		try {
			String sql = "SELECT * FROM korisnik WHERE admin=? AND izbrisan=0";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, isAdmin);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Korisnik korisnik = new Korisnik();
				korisnik.setId(resultSet.getInt("id"));
				korisnik.setKorisnickoIme(resultSet.getString("korisnicko_ime"));
				korisnik.setLozinka(resultSet.getString("lozinka"));
				korisnik.setDatumRegistracije(resultSet.getTimestamp("datum_registracije"));
				korisnik.setAdmin(resultSet.getBoolean("admin"));
				korisnik.setBlokiran(resultSet.getBoolean("blokiran"));
				korisnici.add(korisnik);
				
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return korisnici;
	}
	
	public static Korisnik getAllByUsername(String username){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String sql = "SELECT * FROM korisnik WHERE korisnicko_ime=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Korisnik korisnik = new Korisnik();
				korisnik.setId(resultSet.getInt("id"));
				korisnik.setKorisnickoIme(resultSet.getString("korisnicko_ime"));
				korisnik.setLozinka(resultSet.getString("lozinka"));
				korisnik.setDatumRegistracije(resultSet.getTimestamp("datum_registracije"));
				korisnik.setAdmin(resultSet.getBoolean("admin"));
				korisnik.setBlokiran(resultSet.getBoolean("blokiran"));
				korisnik.setIzbrisan(resultSet.getBoolean("izbrisan"));
				return korisnik;
				
			}
		}catch (Exception e) {
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
		
	


}
