package storage;

public class Capsula {
	private Integer id;
	private float prezzo_orario;
	private String tipologia;
	
	
	
	public Capsula(Integer id, float prezzo_orario, String tipologia) {
		super();
		this.id = id;
		this.prezzo_orario = prezzo_orario;
		this.tipologia = tipologia;
	}



	public Capsula() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public float getPrezzo_orario() {
		return prezzo_orario;
	}



	public void setPrezzo_orario(float prezzo_orario) {
		this.prezzo_orario = prezzo_orario;
	}



	public String getTipologia() {
		return tipologia;
	}



	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	

	
	
	
}
