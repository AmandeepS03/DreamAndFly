package storage;

public class Prenotazione {
	int codiceDiAccesso;
	String orarioInizio;
	String orarioFine;
	String dataInizio;
	String dataFine;
	float prezzoTotale;
	String dataEffettuazione;
	boolean validita;
	float rimborso;
	String userAccountEmail;
	int capsulaId;
	public int getCodiceDiAccesso() {
		return codiceDiAccesso;
	}
	public void setCodiceDiAccesso(int codiceDiAccesso) {
		this.codiceDiAccesso = codiceDiAccesso;
	}
	public String getOrarioInizio() {
		return orarioInizio;
	}
	public void setOrarioInizio(String orarioInizio) {
		this.orarioInizio = orarioInizio;
	}
	public String getOrarioFine() {
		return orarioFine;
	}
	public void setOrarioFine(String orarioFine) {
		this.orarioFine = orarioFine;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public float getPrezzoTotale() {
		return prezzoTotale;
	}
	public void setPrezzoTotale(float prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}
	public String getDataEffettuazione() {
		return dataEffettuazione;
	}
	public void setDataEffettuazione(String dataEffettuazione) {
		this.dataEffettuazione = dataEffettuazione;
	}
	public boolean isValidita() {
		return validita;
	}
	public void setValidita(boolean validita) {
		this.validita = validita;
	}
	public float getRimborso() {
		return rimborso;
	}
	public void setRimborso(float rimborso) {
		this.rimborso = rimborso;
	}
	public String getUserAccountEmail() {
		return userAccountEmail;
	}
	public void setUserAccountEmail(String userAccountEmail) {
		this.userAccountEmail = userAccountEmail;
	}
	public int getCapsulaId() {
		return capsulaId;
	}
	public void setCapsulaId(int capsulaId) {
		this.capsulaId = capsulaId;
	}
	public Prenotazione(int codiceDiAccesso, String orarioInizio, String orarioFine, String dataInizio, String dataFine,
			float prezzoTotale, String dataEffettuazione, boolean validita, float rimborso, String userAccountEmail,
			int capsulaId) {
		super();
		this.codiceDiAccesso = codiceDiAccesso;
		this.orarioInizio = orarioInizio;
		this.orarioFine = orarioFine;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.prezzoTotale = prezzoTotale;
		this.dataEffettuazione = dataEffettuazione;
		this.validita = validita;
		this.rimborso = rimborso;
		this.userAccountEmail = userAccountEmail;
		this.capsulaId = capsulaId;
	}
	public Prenotazione() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
