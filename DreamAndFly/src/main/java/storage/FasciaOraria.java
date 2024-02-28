package storage;

public class FasciaOraria {
	private int numero;
	private String orarioInizio;
	private String orarioFine;
	public FasciaOraria(int numero, String orarioInizio, String orarioFine) {
		super();
		this.numero = numero;
		this.orarioInizio = orarioInizio;
		this.orarioFine = orarioFine;
	}
	public FasciaOraria() {
		super();
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getorarioInizio() {
		return orarioInizio;
	}
	public void setorarioInizio(String orarioInizio) {
		this.orarioInizio = orarioInizio;
	}
	public String getorarioFine() {
		return orarioFine;
	}
	public void setorarioFine(String orarioFine) {
		this.orarioFine = orarioFine;
	}
	
	
	
}
