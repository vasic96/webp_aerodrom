package vasic.web.programiranje.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vasic.web.programiranje.model.Rezervacija;
import vasic.web.programiranje.tools.ConnectionManager;

public class RezervacijaDAO {
	
	public static List<Integer> dajZauzetaMesta(int letId){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Integer> zauzetaMesta = new ArrayList<Integer>();
		try {
			String query = "SELECT * FROM rezervacija WHERE polazni_let_id=? OR povratni_let_id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, letId);
			preparedStatement.setInt(2, letId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int polazniAerodromId = resultSet.getInt(2);
//				int dolazniAerodromId = resultSet.getInt(3);
				if(polazniAerodromId==letId) {
					zauzetaMesta.add(resultSet.getInt(4));
				} else {
					zauzetaMesta.add(resultSet.getInt(5));
				}
				
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
		
		return zauzetaMesta;
	
	}
	
	public static boolean daLiLetImaProdateKarte(int letId) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			String query = "SELECT COUNT(id) FROM rezervacija WHERE (polazni_let_id=? OR povratni_let_id=?) AND datum_prodaje_karte IS NOT NULL";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, letId);
			preparedStatement.setInt(2, letId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {	
				count = resultSet.getInt(1);
			}
			
			return count > 0;
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
		
		return false;
	}
	
	public static boolean daLiKorisnikImaProdateKarte(String username) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			String query = "SELECT COUNT(id) FROM rezervacija WHERE korisnicko_ime = ? AND datum_prodaje_karte IS NOT NULL";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {	
				count = resultSet.getInt(1);
			}
			
			return count > 0;
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
		
		return false;
	}
	
	public static boolean izbrisiRezervaciju(int rezervacijaId) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM rezervacija WHERE id = ? AND datum_prodaje_karte IS NULL";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, rezervacijaId);
			return preparedStatement.executeUpdate() == 1;

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
		
		return false;
	}
	
	
	public static boolean izbrisiRezervacijeZaLet(int letId) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM rezervacija WHERE datum_prodaje_karte IS NULL AND (polazni_let_id = ? OR povratni_let_id = ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, letId);
			preparedStatement.setInt(2, letId);
			return preparedStatement.executeUpdate() == 1;

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
		
		return false;
	}
	
	public static boolean izbrisiRezervacijeZaKorisnika(String username) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM rezervacija WHERE datum_prodaje_karte IS NULL AND korisnicko_ime=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			return preparedStatement.executeUpdate() == 1;

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
		
		return false;
	}
	
	
	
	
	
	public static List<Rezervacija> rezervacijeZaLet(int letId){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		try {
			String query = "SELECT * FROM rezervacija WHERE polazni_let_id=? OR povratni_let_id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, letId);
			preparedStatement.setInt(2, letId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				Rezervacija rezervacija = new Rezervacija();
				rezervacija.setId(resultSet.getInt("id"));
				rezervacija.setPolazniLet(LetDAO.getById(resultSet.getInt("polazni_let_id")));
				int povratniLetId = resultSet.getInt("povratni_let_id");
				if(povratniLetId>0) {
					rezervacija.setPovratniLet(LetDAO.getById(povratniLetId));
				} else {
					rezervacija.setPovratniLet(null);
				}
				rezervacija.setSedistePolazniLet(resultSet.getInt("sediste_polazni_let"));
				rezervacija.setSedistePovratniLet(resultSet.getInt("sediste_povratni_let"));
				rezervacija.setDatumRezervacije(resultSet.getTimestamp("datum_rezervacije"));
				rezervacija.setDatumProdajeKarte(resultSet.getTimestamp("datum_prodaje_karte"));
				rezervacija.setKorisnik(KorisnikDAO.getAllByUsername(resultSet.getString("korisnicko_ime")));
				rezervacija.setImePutnika(resultSet.getString("ime_putnika"));
				rezervacija.setPrezimePutnika(resultSet.getString("prezime_putnika"));
				rezervacija.setUkupnaCena(resultSet.getFloat("ukupna_cena"));
				rezervacije.add(rezervacija);
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
		
		return rezervacije;
	
	}
	
	public static List<Rezervacija> sveREzervacije(){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		try {
			String query = "SELECT * FROM rezervacija";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				Rezervacija rezervacija = new Rezervacija();
				rezervacija.setId(resultSet.getInt("id"));
				rezervacija.setPolazniLet(LetDAO.getById(resultSet.getInt("polazni_let_id")));
				int povratniLetId = resultSet.getInt("povratni_let_id");
				if(povratniLetId>0) {
					rezervacija.setPovratniLet(LetDAO.getById(povratniLetId));
				} else {
					rezervacija.setPovratniLet(null);
				}
				rezervacija.setSedistePolazniLet(resultSet.getInt("sediste_polazni_let"));
				rezervacija.setSedistePovratniLet(resultSet.getInt("sediste_povratni_let"));
				rezervacija.setDatumRezervacije(resultSet.getTimestamp("datum_rezervacije"));
				rezervacija.setDatumProdajeKarte(resultSet.getTimestamp("datum_prodaje_karte"));
				rezervacija.setKorisnik(KorisnikDAO.getAllByUsername(resultSet.getString("korisnicko_ime")));
				rezervacija.setImePutnika(resultSet.getString("ime_putnika"));
				rezervacija.setPrezimePutnika(resultSet.getString("prezime_putnika"));
				rezervacija.setUkupnaCena(resultSet.getFloat("ukupna_cena"));
				rezervacije.add(rezervacija);
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
		
		return rezervacije;
	
	}
	
	public static Rezervacija getById(int id){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM rezervacija WHERE id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				Rezervacija rezervacija = new Rezervacija();
				rezervacija.setId(resultSet.getInt("id"));
				rezervacija.setPolazniLet(LetDAO.getById(resultSet.getInt("polazni_let_id")));
				int povratniLetId = resultSet.getInt("povratni_let_id");
				if(povratniLetId>0) {
					rezervacija.setPovratniLet(LetDAO.getById(povratniLetId));
				} else {
					rezervacija.setPovratniLet(null);
				}
				rezervacija.setSedistePolazniLet(resultSet.getInt("sediste_polazni_let"));
				rezervacija.setSedistePovratniLet(resultSet.getInt("sediste_povratni_let"));
				rezervacija.setDatumRezervacije(resultSet.getTimestamp("datum_rezervacije"));
				rezervacija.setDatumProdajeKarte(resultSet.getTimestamp("datum_prodaje_karte"));
				rezervacija.setKorisnik(KorisnikDAO.getAllByUsername(resultSet.getString("korisnicko_ime")));
				rezervacija.setImePutnika(resultSet.getString("ime_putnika"));
				rezervacija.setPrezimePutnika(resultSet.getString("prezime_putnika"));
				rezervacija.setUkupnaCena(resultSet.getFloat("ukupna_cena"));
				
				return rezervacija;
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
		
		return null;
	
	}
	
	
	public static boolean kupiRezervaciju(int id){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE rezervacija SET datum_prodaje_karte = ? WHERE id = ? AND datum_prodaje_karte is null";
			preparedStatement = connection.prepareStatement(query);
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatement.setTimestamp(1, sqlDate);
			preparedStatement.setInt(2, id);			
			return preparedStatement.executeUpdate()==1;
			
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
		
		return false;
	
	}
	
	public static boolean promeniImePrezime(String ime, String prezime, int id){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE rezervacija SET ime_putnika = ?, prezime_putnika = ? WHERE id = ? AND datum_prodaje_karte is null;";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ime);
			preparedStatement.setString(2, prezime);
			preparedStatement.setInt(3, id);
			return preparedStatement.executeUpdate()==1;
			
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
		
		return false;
	
	}
	
	public static boolean promeniSediste(int sediste,String tip, int id){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String query = "";
			if(tip.equals("polaznu")) {
				query += "UPDATE rezervacija SET sediste_polazni_let = ? WHERE id = ? AND datum_prodaje_karte is null;";
			} else {
				query += "UPDATE rezervacija SET sediste_povratni_let = ? WHERE id = ? AND datum_prodaje_karte is null;";
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, sediste);
			preparedStatement.setInt(2, id);
			return preparedStatement.executeUpdate()==1;
			
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
		
		return false;
	
	}
	
	
	
	
	public static List<Rezervacija> rezervacijeZaKorisnika(String username){
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		try {
			String query = "SELECT * FROM rezervacija WHERE korisnicko_ime=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				Rezervacija rezervacija = new Rezervacija();
				rezervacija.setId(resultSet.getInt("id"));
				rezervacija.setPolazniLet(LetDAO.getById(resultSet.getInt("polazni_let_id")));
				int povratniLetId = resultSet.getInt("povratni_let_id");
				rezervacija.setPovratniLet( povratniLetId > 0 ? LetDAO.getById(povratniLetId) : null );
				rezervacija.setSedistePolazniLet(resultSet.getInt("sediste_polazni_let"));
				rezervacija.setSedistePovratniLet(resultSet.getInt("sediste_povratni_let"));
				rezervacija.setDatumRezervacije(resultSet.getTimestamp("datum_rezervacije"));
				rezervacija.setDatumProdajeKarte(resultSet.getTimestamp("datum_prodaje_karte"));
				rezervacija.setKorisnik(KorisnikDAO.getAllByUsername(resultSet.getString("korisnicko_ime")));
				rezervacija.setImePutnika(resultSet.getString("ime_putnika"));
				rezervacija.setPrezimePutnika(resultSet.getString("prezime_putnika"));
				rezervacija.setUkupnaCena(resultSet.getFloat("ukupna_cena"));
				rezervacije.add(rezervacija);
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
		
		return rezervacije;
	
	}
	
	public static boolean dodajRezervaciju(Rezervacija rezervacija) {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "INSERT INTO rezervacija ( polazni_let_id,povratni_let_id,sediste_polazni_let, sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena) values (?,?,?,?,?,?,?,?,?,?);";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, rezervacija.getPolazniLet().getId());
			if(rezervacija.getPovratniLet()!=null) {
				preparedStatement.setInt(2, rezervacija.getPovratniLet().getId());

			} else {
				preparedStatement.setInt(2,0);

			}
			preparedStatement.setInt(3, rezervacija.getSedistePolazniLet());
			preparedStatement.setInt(4, rezervacija.getSedistePovratniLet());
			java.sql.Timestamp sqlRezervacija = new java.sql.Timestamp(rezervacija.getDatumRezervacije().getTime());
			if(rezervacija.getDatumProdajeKarte()!=null) {
				java.sql.Timestamp sqlProdaja = new java.sql.Timestamp(rezervacija.getDatumProdajeKarte().getTime());
				preparedStatement.setTimestamp(6, sqlProdaja);
			} else {
				preparedStatement.setTimestamp(6, null);
			}
			preparedStatement.setTimestamp(5, sqlRezervacija);
			preparedStatement.setString(7, rezervacija.getKorisnik().getKorisnickoIme());
			preparedStatement.setString(8, rezervacija.getImePutnika());
			preparedStatement.setString(9, rezervacija.getPrezimePutnika());
			preparedStatement.setFloat(10, rezervacija.getUkupnaCena());
			
			return preparedStatement.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {preparedStatement.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		
		return false;
	}
	

}
