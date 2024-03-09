package applicationLogic.prenotazioneManagement;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
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
import storage.FasciaOrariaDao;
import storage.Prenotabile;
import storage.PrenotabileDao;

/**
 * Servlet implementation class PagamentoServlet
 */
@WebServlet("/PagamentoServlet")
public class PagamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();

       
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
			
			String orario_inizio = (String) request.getSession().getAttribute("orarioInizio");
			String orario_fine = (String) request.getSession().getAttribute("orarioFine");
			String dataInizio = (String) request.getSession().getAttribute("dataInizio");
			String dataFine = (String) request.getSession().getAttribute("dataFine");
			Float prezzoTotale = Float.valueOf((String) request.getSession().getAttribute("prezzo"));
			Integer capsula_id = Integer.valueOf((String) request.getSession().getAttribute("capsulaId"));
			
			System.out.println("orario inizio: "+ orario_inizio);
			System.out.println("orario Fine: "+ orario_fine);
			System.out.println("data Inizio: "+ dataInizio);
			System.out.println("data fine: "+ dataFine);
			System.out.println("id: "+ capsula_id);
			
			//dataEffettuazione è la data odierna
			LocalDate dataCorrente = LocalDate.now();
	        // Definire un formato per la data
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	        // Trasformare la data in stringa
	        String dataEffettuazione = dataCorrente.format(formatter);
	        
	        AccountUser user = (AccountUser) request.getSession().getAttribute("auth");	//TODO
	        String account_user_email = user.getEmail();
	 
	              
	        
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
	        LocalDate dataInizioDate = LocalDate.parse(dataInizio);
			LocalDate dataFineDate = LocalDate.parse(dataFine);
	        FasciaOrariaDao toolFasciaOraria = new FasciaOrariaDao(ds);
	        int orarioInizioInt=0;
	        int orarioFineInt=0;
	        PrenotabileDao toolPrenotabile=new PrenotabileDao(ds);
				        
	        try {
				orarioInizioInt = toolFasciaOraria.doRetrieveByOrarioInizio(orario_inizio);
				orarioFineInt = toolFasciaOraria.doRetrieveByOrarioFine(orario_fine);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			if(dataInizioDate.equals(dataFineDate)) {
				for(int fascia = orarioInizioInt; fascia<= orarioFineInt; fascia ++) {
					try {
						toolPrenotabile.doDelete(dataInizio,capsula_id,fascia);						
					}catch (SQLException e){
						logger.log(Level.WARNING, "Problema Sql!",e);
					}
				}		
			}else {
				for(;dataInizioDate.isBefore(dataFineDate)|| dataInizioDate.equals(dataFineDate); dataInizioDate = dataInizioDate.plusDays(1)) {
					if(dataInizioDate.equals(dataFineDate)) {
						for(; orarioInizioInt<= orarioFineInt; orarioInizioInt ++) {
							try {
								toolPrenotabile.doDelete(dataInizioDate.toString(),capsula_id,orarioInizioInt);						
							}catch (SQLException e){
								logger.log(Level.WARNING, "Problema Sql!",e);
							}
						}		
					}else {
					for(;orarioInizioInt<= 24; orarioInizioInt++) {
						try {
							toolPrenotabile.doDelete(dataInizio.toString(),capsula_id,orarioInizioInt);						
						}catch (SQLException e){
							logger.log(Level.WARNING, "Problema Sql!",e);
						}
					}
					orarioInizioInt=1;
				}
				}
				
				
				
				
				
			}
	        
	        
			
		
		//ridirezionare a confermaPagamento.jsp: salva il codice generato(request.setAttribute()) e scrivilo in conferma pagamento.
	        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/UtenteRegistratoGUI/ConfermaPrenotazione.jsp");
		    dispatcher.forward(request, response);	
	}

}
