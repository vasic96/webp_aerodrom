package vasic.web.programiranje.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.model.Let;

public class LetDTO {
	private int id;
	private String broj;
	private Date vremePolaska;
	private Date vremeDolaska;
	private int polazniAerodromId;
	private int dolazniAerodromId;
	private String dolazniAerodromName;
	private String polazniAerodromName;
	private int brojSedista;
	private int brojSlobodnihSedista;
	private float cena;
	private boolean izbrisan;
	private boolean nevazeci;
	
	 
	

	
	public LetDTO(Let let) {
		
		
		
		this.id = let.getId();
		this.broj = let.getBroj();
		this.vremePolaska = let.getVremePolaska();
		this.vremeDolaska = let.getVremeDolaska();
		this.polazniAerodromId = let.getPolazniAerodrom().getId();
		this.dolazniAerodromId = let.getDolazniAerodrom().getId();
		this.dolazniAerodromName = let.getDolazniAerodrom().getNaziv();
		this.polazniAerodromName = let.getPolazniAerodrom().getNaziv();
		this.brojSedista = let.getBrojSedista();
		this.cena = let.getCena();
		 List<Integer> zauzetaMestaZaLet = RezervacijaDAO.dajZauzetaMesta(let.getId());
		 List<Integer> slobodnaMesta = new ArrayList<Integer>();
		 for(int i = 1;i<let.getBrojSedista()+1;i++) {
			 if(!zauzetaMestaZaLet.contains(i)) {
				 slobodnaMesta.add(i);
			 }
		 }
		this.nevazeci = (let.getVremePolaska().after(new Date())) ? false : true;
		this.brojSlobodnihSedista = slobodnaMesta.size();
		this.izbrisan = let.isIzbrisan();
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

	public int getPolazniAerodromId() {
		return polazniAerodromId;
	}

	public void setPolazniAerodromId(int polazniAerodromId) {
		this.polazniAerodromId = polazniAerodromId;
	}

	public int getDolazniAerodromId() {
		return dolazniAerodromId;
	}

	public void setDolazniAerodromId(int dolazniAerodromId) {
		this.dolazniAerodromId = dolazniAerodromId;
	}

	public int getBrojSedista() {
		return brojSedista;
	}

	public void setBrojSedista(int brojSedista) {
		this.brojSedista = brojSedista;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}
	
	

	public String getDolazniAerodromName() {
		return dolazniAerodromName;
	}

	public void setDolazniAerodromName(String dolazniAerodromName) {
		this.dolazniAerodromName = dolazniAerodromName;
	}

	public String getPolazniAerodromName() {
		return polazniAerodromName;
	}

	public void setPolazniAerodromName(String polazniAerodromName) {
		this.polazniAerodromName = polazniAerodromName;
	}
	

	public int getBrojSlobodnihSedista() {
		return brojSlobodnihSedista;
	}

	public void setBrojSlobodnihSedista(int brojSlobodnihSedista) {
		this.brojSlobodnihSedista = brojSlobodnihSedista;
	}
	

	public boolean isIzbrisan() {
		return izbrisan;
	}

	public void setIzbrisan(boolean izbrisan) {
		this.izbrisan = izbrisan;
	}

	@Override
	public String toString() {
		return "LetDTO [id=" + id + ", broj=" + broj + ", vremePolaska=" + vremePolaska + ", vremeDolaska="
				+ vremeDolaska + ", polazniAerodromId=" + polazniAerodromId + ", dolazniAerodromId=" + dolazniAerodromId
				+ ", brojSedista=" + brojSedista + ", cena=" + cena + "]";
	}
	
	
	
	

}
