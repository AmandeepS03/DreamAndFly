package utils;

import storage.Prenotazione;

public class PrenotazioneWrapper {
    private Prenotazione prenotazione;
    private String tipologiaCapsula;

    public PrenotazioneWrapper(Prenotazione prenotazione, String tipologiaCapsula) {
        this.prenotazione = prenotazione;
        this.tipologiaCapsula = tipologiaCapsula;
    }

    public PrenotazioneWrapper() {
		// TODO Auto-generated constructor stub
	}
    
    

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public void setTipologiaCapsula(String tipologiaCapsula) {
		this.tipologiaCapsula = tipologiaCapsula;
	}

	public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public String getTipologiaCapsula() {
        return tipologiaCapsula;
    }
}
