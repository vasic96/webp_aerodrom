package vasic.web.programiranje.dto;

import java.util.Date;

import vasic.web.programiranje.model.Rezervacija;

public class RezervacijaDTO {
	
	private int id;
	private int polazniLetId;
	private int povratniLetId;
	private int sedistePolazniLet;
	private int sedistePovratniLet;
	private Date datumRezervacije;
	private Date datumProdajeKarte;
	private String korisnikUsername;
	private String imePutnika;
	private String prezimePutnika;
	private float ukupnaCena;
	private String polazniLetAerodrom;
	private String povratniLetAerodrom;
	
	public RezervacijaDTO() {}
	
	public RezervacijaDTO(Rezervacija rezervacija ) {
		this.id =  rezervacija.getId();
		this.polazniLetId = rezervacija.getPolazniLet().getId();
		this.povratniLetId = (rezervacija.getPovratniLet()==null ? 0 : rezervacija.getPovratniLet().getId());
		this.sedistePolazniLet = rezervacija.getSedistePolazniLet();
		this.sedistePovratniLet = rezervacija.getSedistePovratniLet();
		this.datumRezervacije = rezervacija.getDatumRezervacije();
		this.datumProdajeKarte = rezervacija.getDatumProdajeKarte();
		this.korisnikUsername = rezervacija.getKorisnik().getKorisnickoIme();
		this.imePutnika = rezervacija.getImePutnika();
		this.prezimePutnika = rezervacija.getPrezimePutnika();
		this.ukupnaCena = rezervacija.getUkupnaCena();
		this.polazniLetAerodrom = rezervacija.getPolazniLet().getPolazniAerodrom().getNaziv();
		this.povratniLetAerodrom = (rezervacija.getPovratniLet()==null ? "": rezervacija.getPovratniLet().getPolazniAerodrom().getNaziv());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPolazniLetId() {
		return polazniLetId;
	}

	public void setPolazniLetId(int polazniLetId) {
		this.polazniLetId = polazniLetId;
	}

	public int getPovratniLetId() {
		return povratniLetId;
	}

	public void setPovratniLetId(int povratniLetId) {
		this.povratniLetId = povratniLetId;
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

	public String getKorisnikUsername() {
		return korisnikUsername;
	}

	public void setKorisnikUsername(String korisnikUsername) {
		this.korisnikUsername = korisnikUsername;
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
	
	

	public String getPolazniLetAerodrom() {
		return polazniLetAerodrom;
	}

	public void setPolazniLetAerodrom(String polazniLetAerodrom) {
		this.polazniLetAerodrom = polazniLetAerodrom;
	}

	public String getPovratniLetAerodrom() {
		return povratniLetAerodrom;
	}

	public void setPovratniLetAerodrom(String povratniLetAerodrom) {
		this.povratniLetAerodrom = povratniLetAerodrom;
	}

	@Override
	public String toString() {
		return "RezervacijaDTO [id=" + id + ", polazniLetId=" + polazniLetId + ", povratniLetId=" + povratniLetId
				+ ", sedistePolazniLet=" + sedistePolazniLet + ", sedistePovratniLet=" + sedistePovratniLet
				+ ", datumRezervacije=" + datumRezervacije + ", datumProdajeKarte=" + datumProdajeKarte
				+ ", korisnikUsername=" + korisnikUsername + ", imePutnika=" + imePutnika + ", prezimePutnika="
				+ prezimePutnika + ", ukupnaCena=" + ukupnaCena + "]";
	}
	
	
	
	
	
	
}
