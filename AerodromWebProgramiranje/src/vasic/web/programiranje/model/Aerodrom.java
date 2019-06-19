package vasic.web.programiranje.model;

public class Aerodrom {
	
	private int id;
	private String naziv;
	
	public Aerodrom() {}
	
	
	
	public Aerodrom(int id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}



	@Override
	public String toString() {
		return "Aerodrom [id=" + id + ", naziv=" + naziv + "]";
	}
	
	
	
	

}
