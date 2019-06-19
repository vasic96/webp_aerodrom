package vasic.web.programiranje.model;
import java.util.Date;

import vasic.web.programiranje.dao.AerodromDAO;
import vasic.web.programiranje.dto.LetDTO;


public class Let {
	private int id;
	private String broj;
	private Date vremePolaska;
	private Date vremeDolaska;
	private Aerodrom polazniAerodrom;
	private Aerodrom dolazniAerodrom;
	private int brojSedista;
	private float cena;	
	private boolean izbrisan;
	public Let() {}
	
	public Let(LetDTO letDTO) {
		if(letDTO.getId()!=0) {
			this.id = letDTO.getId();
		}
		if(letDTO.getVremePolaska().after(new Date()) && letDTO.getVremeDolaska().after(letDTO.getVremePolaska())){
			this.vremePolaska = letDTO.getVremePolaska();
			this.vremeDolaska = letDTO.getVremeDolaska();
		} else {
			throw new IllegalArgumentException("Datumi nisu ok");
		}
		this.setPolazniAerodrom(AerodromDAO.getOne(letDTO.getPolazniAerodromId()));
		this.setDolazniAerodrom(AerodromDAO.getOne(letDTO.getDolazniAerodromId()));
		if(letDTO.getBrojSedista()>0) {
			this.brojSedista = letDTO.getBrojSedista();
		} else {
			throw new IllegalArgumentException("Broj sedista mora biti veci od 0!");
		}
		if(letDTO.getCena()>0) {
			this.cena = letDTO.getCena();
		} else {
			throw new IllegalArgumentException("Cena mora biti veca od 0!");
		}
	}

	public Let(int id, String broj, Date vremePolaska, Date vremeDolaska, Aerodrom polazniAerodrom,
			Aerodrom dolazniAerodrom, int brojSedista, float cena) {
		super();
		this.id = id;
		this.broj = broj;
		this.vremePolaska = vremePolaska;
		this.vremeDolaska = vremeDolaska;
		this.polazniAerodrom = polazniAerodrom;
		this.dolazniAerodrom = dolazniAerodrom;
		if(brojSedista>0) {
			this.brojSedista = brojSedista;
		} else {
			throw new IllegalArgumentException("Broj sedista mora biti veci od 0!");
		}
		if(cena>0) {
			this.cena = cena;
		} else {
			throw new IllegalArgumentException("Cena mora biti veca od 0!");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public Date getVremePolaska() {
		return vremePolaska;
	}

	public void setVremePolaska(Date vremePolaska) {
		this.vremePolaska = vremePolaska;
	}

	public Date getVremeDolaska() {
		return vremeDolaska;
	}

	public void setVremeDolaska(Date vremeDolaska) {
		this.vremeDolaska = vremeDolaska;
	}

	public Aerodrom getPolazniAerodrom() {
		return polazniAerodrom;
	}

	public void setPolazniAerodrom(Aerodrom polazniAerodrom) {
		this.polazniAerodrom = polazniAerodrom;
	}

	public Aerodrom getDolazniAerodrom() {
		return dolazniAerodrom;
	}

	public void setDolazniAerodrom(Aerodrom dolazniAerodrom) {
		this.dolazniAerodrom = dolazniAerodrom;
	}

	public int getBrojSedista() {
		return brojSedista;
	}

	public void setBrojSedista(int brojSedista) {
		if(brojSedista>0) {
			this.brojSedista = brojSedista;
		} else {
			throw new IllegalArgumentException("Broj sedista mora biti veci od 0!");
		}
	}
	
	

	public boolean isIzbrisan() {
		return izbrisan;
	}

	public void setIzbrisan(boolean izbrisan) {
		this.izbrisan = izbrisan;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		if(cena>0) {
			this.cena = cena;
		} else {
			throw new IllegalArgumentException("Cena mora biti veci od 0!");
		}
	}
	
	

}
