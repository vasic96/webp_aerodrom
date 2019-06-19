package vasic.web.programiranje.model;

import java.io.Serializable;
import java.util.Date;

public class Korisnik implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String korisnickoIme;
	private String lozinka;
	private Date datumRegistracije;
	private boolean admin;
	private boolean blokiran;
	private boolean izbrisan;
	
	public Korisnik() {}
	
	

	public Korisnik(int id, String korisnickoIme, String lozinka, Date datumRegistracije, boolean isAdmin,boolean blokiran) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.datumRegistracije = datumRegistracije;
		this.admin = isAdmin;
		this.blokiran=blokiran;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Date getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(Date datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}



	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}



	public boolean isBlokiran() {
		return blokiran;
	}



	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}



	public boolean isIzbrisan() {
		return izbrisan;
	}



	public void setIzbrisan(boolean izbrisan) {
		this.izbrisan = izbrisan;
	}
	
	

	

	
	
	

}
