package vasic.web.programiranje.model;

import java.util.ArrayList;
import java.util.List;

public class IzvestajUkupno {

	private List<Izvestaj> izvestaji;
	private int ukupanBrojLetova;
	private int ukupanBrojProdatihKarata;
	private int ukupnaCenaProdatihKarata;
	
	
	public IzvestajUkupno() {
		this.izvestaji = new ArrayList<Izvestaj>();
	}


	
	public void addIzvestaj(Izvestaj izvestaj) {
		this.izvestaji.add(izvestaj);
		this.ukupanBrojLetova+=izvestaj.getBrojLetova();
		this.ukupanBrojProdatihKarata+=izvestaj.getBrojProdatihKarata();
		this.ukupnaCenaProdatihKarata+=izvestaj.getUkupnaCenaKarata();
	}


	public List<Izvestaj> getIzvestaj() {
		return izvestaji;
	}




	public void setIzvestaj(List<Izvestaj> izvestaj) {
		this.izvestaji = izvestaj;
	}




	public int getUkupanBrojLetova() {
		return ukupanBrojLetova;
	}


	public void setUkupanBrojLetova(int ukupanBrojLetova) {
		this.ukupanBrojLetova = ukupanBrojLetova;
	}


	public int getUkupanBrojProdatihKarata() {
		return ukupanBrojProdatihKarata;
	}


	public void setUkupanBrojProdatihKarata(int ukupanBrojProdatihKarata) {
		this.ukupanBrojProdatihKarata = ukupanBrojProdatihKarata;
	}


	public int getUkupnaCenaProdatihKarata() {
		return ukupnaCenaProdatihKarata;
	}


	public void setUkupnaCenaProdatihKarata(int ukupnaCenaProdatihKarata) {
		this.ukupnaCenaProdatihKarata = ukupnaCenaProdatihKarata;
	}
	
	

}
