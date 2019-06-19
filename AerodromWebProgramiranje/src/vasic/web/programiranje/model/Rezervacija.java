package vasic.web.programiranje.model;

import java.util.Date;

public class Rezervacija {
	
	private int id;
	private Let polazniLet;
	private Let povratniLet;
	private int sedistePolazniLet;
	private int sedistePovratniLet;
	private Date datumRezervacije;
	private Date datumProdajeKarte;
	private Korisnik korisnik;
	private String imePutnika;
	private String prezimePutnika;
	private float ukupnaCena;
	
	public Rezervacija() {}

	public Rezervacija(int id, Let polazniLet, Let povratniLet, int sedistePolazniLet, int sedistePovratniLet,
			Date datumRezervacije, Date datumProdajeKarte, Korisnik korisnik, String imePutnika,
			String prezimePutnika) {
		super();
		this.id = id;
		this.polazniLet = polazniLet;
		this.povratniLet = povratniLet;
		this.sedistePolazniLet = sedistePolazniLet;
		this.sedistePovratniLet = sedistePovratniLet;
		this.datumRezervacije = datumRezervacije;
		this.datumProdajeKarte = datumProdajeKarte;
		this.korisnik = korisnik;
		this.imePutnika = imePutnika;
		this.prezimePutnika = prezimePutnika;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Let getPolazniLet() {
		return polazniLet;
	}

	public void setPolazniLet(Let polazniLet) {
		this.polazniLet = polazniLet;
	}

	public Let getPovratniLet() {
		return povratniLet;
	}

	public void setPovratniLet(Let povratniLet) {
		this.povratniLet = povratniLet;
	}

	public int getSedistePolazniLet() {
		return sedistePolazniLet;
	}

	public void setSedistePolazniLet(int sedistePolazniLet) {
		this.sedistePolazniLet = sedistePolazniLet;
	}

	public int getSedistePovratniLet() {
		return sedistePovratniLet;
	}

	public void setSedistePovratniLet(int sedistePovratniLet) {
		this.sedistePovratniLet = sedistePovratniLet;
	}

	public Date getDatumRezervacije() {
		return datumRezervacije;
	}

	public void setDatumRezervacije(Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

	public Date getDatumProdajeKarte() {
		return datumProdajeKarte;
	}

	public void setDatumProdajeKarte(Date datumProdajeKarte) {
		this.datumProdajeKarte = datumProdajeKarte;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public String getImePutnika() {
		return imePutnika;
	}

	public void setImePutnika(String imePutnika) {
		this.imePutnika = imePutnika;
	}

	public String getPrezimePutnika() {
		return prezimePutnika;
	}

	public void setPrezimePutnika(String prezimePutnika) {
		this.prezimePutnika = prezimePutnika;
	}

	public float getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(float ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	@Override
	public String toString() {
		return "Rezervacija [id=" + id + ", polazniLet=" + polazniLet + ", povratniLet=" + povratniLet
				+ ", sedistePolazniLet=" + sedistePolazniLet + ", sedistePovratniLet=" + sedistePovratniLet
				+ ", datumRezervacije=" + datumRezervacije + ", datumProdajeKarte=" + datumProdajeKarte + ", korisnik="
				+ korisnik + ", imePutnika=" + imePutnika + ", prezimePutnika=" + prezimePutnika + ", ukupnaCena="
				+ ukupnaCena + "]";
	}
	
	
	
	
	
	
	

}
