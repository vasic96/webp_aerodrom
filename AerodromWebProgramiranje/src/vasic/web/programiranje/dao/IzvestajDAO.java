package vasic.web.programiranje.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.tools.ConnectionManager;

public class IzvestajDAO {
	
	
	public static int getBrojLetova(int aerodromId,Date datumOd, Date datumDo) {
		
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		int broj = 0;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT COUNT(*) FROM let WHERE dolazni_aerodrom_id = ? AND datum_polaska BETWEEN ? AND ?";
			preparedStatement = connection.prepareStatement(sql);
			java.sql.Timestamp sqlDatumOd = new java.sql.Timestamp(datumOd.getTime());
			java.sql.Timestamp sqlDatumDo = new java.sql.Timestamp(datumDo.getTime());
			preparedStatement.setInt(1, aerodromId);
			preparedStatement.setTimestamp(2, sqlDatumOd);
			preparedStatement.setTimestamp(3, sqlDatumDo);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				broj += resultSet.getInt(1);
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
		return broj;
		
	}
	
	public static int getBrojProdatihKarata(int aerodromId,Date datumOd, Date datumDo) {
		
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		int broj = 0;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT COUNT(*) from rezervacija AS a LEFT JOIN let AS b ON a.polazni_let_id = b.id OR a.povratni_let_id = b.id WHERE b.dolazni_aerodrom_id=? and a.datum_prodaje_karte is not null and b.datum_polaska BETWEEN ? AND ?;";
			preparedStatement = connection.prepareStatement(sql);
			java.sql.Timestamp sqlDatumOd = new java.sql.Timestamp(datumOd.getTime());
			java.sql.Timestamp sqlDatumDo = new java.sql.Timestamp(datumDo.getTime());
			preparedStatement.setInt(1, aerodromId);
			preparedStatement.setTimestamp(2, sqlDatumOd);
			preparedStatement.setTimestamp(3, sqlDatumDo);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				broj += resultSet.getInt(1);
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
		return broj;
		
	}
	
	public static float ukupnaCenaProdatihKarata(int aerodromId,Date datumOd, Date datumDo) {
		
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		float broj = 0;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT SUM(b.cena) from rezervacija AS a LEFT JOIN let AS b ON a.polazni_let_id = b.id OR a.povratni_let_id = b.id WHERE b.dolazni_aerodrom_id=? and a.datum_prodaje_karte is not null and b.datum_polaska BETWEEN ? AND ?;";
			preparedStatement = connection.prepareStatement(sql);
			java.sql.Timestamp sqlDatumOd = new java.sql.Timestamp(datumOd.getTime());
			java.sql.Timestamp sqlDatumDo = new java.sql.Timestamp(datumDo.getTime());
			preparedStatement.setInt(1, aerodromId);
			preparedStatement.setTimestamp(2, sqlDatumOd);
			preparedStatement.setTimestamp(3, sqlDatumDo);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				broj += resultSet.getFloat(1);
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
		return broj;
		
	}

}
