package applicationLogic.prenotazioneManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.AccountUser;
import storage.Capsula;
import storage.Prenotazione;
import storage.PrenotazioneDao;

/**
 * Servlet implementation class PagamentoServlet
 */
@WebServlet("/PagamentoServlet")
public class PagamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagamentoServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Registrare la prenotazione:
			//a) scrivere in prenotazione (query = "doSave()")
			
			String orario_inizio = (String) request.getAttribute("orarioInizio");
			String orario_fine = (String) request.getAttribute("orarioFine");
			String dataInizio = (String) request.getAttribute("dataInizio");
			String dataFine = (String) request.getAttribute("dataFine");
			Float prezzoTotale = (float) request.getAttribute("prezzoTotale");
			
			//dataEffettuazione è la data odierna
			LocalDate dataCorrente = LocalDate.now();
	        // Definire un formato per la data
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	        // Trasformare la data in stringa
	        String dataEffettuazione = dataCorrente.format(formatter);
	        
	        AccountUser user = (AccountUser) request.getSession().getAttribute("auth");	//TODO
	        String account_user_email = user.getEmail();
	        
	        Capsula capsula = (Capsula) request.getAttribute("capsulaPrenotata");	//TODO
	        int capsula_id = capsula.getId();
	        
	        
	        Prenotazione savePrenotazione = new Prenotazione(orario_inizio,orario_fine, dataInizio, dataFine, prezzoTotale, dataEffettuazione, account_user_email, capsula_id);
	        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	        PrenotazioneDao toolPrenotazione = new PrenotazioneDao(ds);
	        try {
				int codiceDiAccesso = toolPrenotazione.doSave(savePrenotazione);
				request.setAttribute("codiceDiAccesso", codiceDiAccesso);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        		
			//b) eliminare da 'e_prenotabile' le date e fasce orarie non piu prenotabili (query=doDelete da chiamare tante quante sono le cose da eliminare. 
								//Per ogni giorno prenotato e per ogni fascia oraria prenotata)
			
		
		//ridirezionare a confermaPagamento.jsp: salva il codice generato(request.setAttribute()) e scrivilo in conferma pagamento.
	}

}
