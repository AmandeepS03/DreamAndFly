package storage;

public class Prenotabile {
	private String dataPrenotabile ; 
	private int capsulaId ;
	private int fasciaOrariaNumero;
	
	
	
	public Prenotabile() {
		super();
	}
	
	public Prenotabile(String dataPrenotabile, int capsulaId, int fasciaOrariaNumero) {
		super();
		this.dataPrenotabile = dataPrenotabile;
		this.capsulaId = capsulaId;
		this.fasciaOrariaNumero = fasciaOrariaNumero;
	}

	public String getDataPrenotabile() {
		return dataPrenotabile;
	}

	public void setDataPrenotabile(String dataPrenotabile) {
		this.dataPrenotabile = dataPrenotabile;
	}

	public int getCapsulaId() {
		return capsulaId;
	}
	public void setCapsulaId(int capsulaId) {
		this.capsulaId = capsulaId;
	}
	public int getFasciaOrariaNumero() {
		return fasciaOrariaNumero;
	}
	public void setFasciaOrariaNumero(int fasciaOrariaNumero) {
		this.fasciaOrariaNumero = fasciaOrariaNumero;
	}
	
	
}
