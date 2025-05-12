package applicationLogic.prenotazioneManagement;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.PrenotazioneDao;
import storage.PrenotabileDao;

/**
 * Servlet implementation class VisualizzaPrenotazioniGestoreFiltri
 */
@WebServlet("/VisualizzaPrenotazioniGestoreFiltri")
public class VisualizzaPrenotazioniGestoreFiltriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaPrenotazioniGestoreFiltriServlet() {
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
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		PrenotazioneDao pDao = new PrenotazioneDao(ds);
		PrenotabileDao prenotabileDao = new PrenotabileDao(ds);
		
		String numeroCapsula = request.getParameter("numeroCapsula");
		String account = request.getParameter("account");
		String dataInizio = request.getParameter("dataInizio");
		String dataFine = request.getParameter("dataFine");
		
		//non inserisce nessun filtro: mostra tutto
		if(vuota(numeroCapsula) && vuota( account) && vuota( dataInizio) && vuota( dataFine) ) {
			try {
				request.setAttribute("listaPrenotazioni", pDao.doRetriveAll());
				request.setAttribute("listaPrenotabili", prenotabileDao.doRetriveAll() );
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		// inserisce numero capsula e data fine
		if(!vuota(numeroCapsula) && vuota( account) && vuota( dataInizio) && !vuota( dataFine) ) {
			try {
				Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula")); 
				request.setAttribute("listaPrenotazioneByCapsulaAndDataFine", pDao.doRetrivePrenotazioneByCapsulaAndDataFine(numeroCapsulaSelect,dataFine));
				request.setAttribute("listaPrenotabiliByCapsulaAndDataFine", prenotabileDao.doRetrivePrenotabiliByCapsulaAndDataFine(numeroCapsulaSelect,dataFine));
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}

		
		//inserisce solo il numero capsula 
		
		  if(!vuota(numeroCapsula) && vuota( account) && vuota( dataInizio) && vuota( dataFine) ) {
			  
			  Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula")); 
			  
				  try {
					request.setAttribute("listaPrenotazioneByCapsula", pDao.doRetrivePrenotazioniByNumeroCapsulaAll(numeroCapsulaSelect));
					request.setAttribute("listaPrenotabiliByCapsula", prenotabileDao.doRetrieveById(numeroCapsulaSelect));
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			  
		  }
		  
		//inserisce il numero capsula e la data di inizio
			
		  
		  if(!vuota(numeroCapsula) && vuota( account) && !vuota( dataInizio) && vuota( dataFine) ) {
			  
			  Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula"));   
				  try {
					request.setAttribute("listaPrenotazioneByCapsulaAndDataInizio", pDao.doRetrivePrenotazioneByCapsulaAndDataInizio(numeroCapsulaSelect,dataInizio));
					request.setAttribute("listaPrenotabiliByCapsulaAndDataInizio", prenotabileDao.doRetrivePrenotabiliByCapsulaAndDataInizio(numeroCapsulaSelect,dataInizio));
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			  
		  }
		  
		  //inserisce il numero capsula ,la data di inizio e la data di fine
			
		  
		  if(!vuota(numeroCapsula) && vuota( account) && !vuota( dataInizio) && !vuota( dataFine) ) {
			  
			  Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula"));   
				  try {
					request.setAttribute("listaPrenotazioneByCapsulaAndDataInizioAndDataFine", pDao.doRetrivePrenotazioneByCapsulaAndDataInizioAndDataFine(numeroCapsulaSelect,dataInizio,dataFine));
					request.setAttribute("listaPrenotabiliByCapsulaAndDataInizioAndDataFine", prenotabileDao.doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine(numeroCapsulaSelect,dataInizio,dataFine));
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			  
		  }
		 
		
		//insersce solo l'account
		if(!vuota( account) && vuota( dataInizio) && vuota( dataFine) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByAccount", pDao.doRetrivePrenotazioniByAccount(request.getParameter("account")));
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
		
		//inserisce solo la data di inizio
		if(!vuota(dataInizio) && vuota( dataFine) && vuota( account) && vuota( numeroCapsula)) {
			try {
				
				request.setAttribute("listaPrenotazioneByDate", pDao.doRetrivePrenotazioniByDataInizio(request.getParameter("dataInizio")));
				request.setAttribute("listaPrenotabileByDateInizio", prenotabileDao.doRetrieveByDataInizio(request.getParameter("dataInizio")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//inserisce solo la data di fine
		if(!vuota( dataFine) &&  vuota( dataInizio) && vuota( account) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByDateFine", pDao.doRetrivePrenotazioniByDataFine(request.getParameter("dataFine")));
				request.setAttribute("listaPrenotabileByDateFine", prenotabileDao.doRetrieveByDataFine(request.getParameter("dataFine")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e la data di fine, ma non il numero capsula e l account 
		if(!vuota(dataInizio) && !vuota(dataFine) && vuota( account) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByDateInizioAndFine", pDao.doRetrivePrenotazioniByDataInizioAndFine(request.getParameter("dataInizio"), request.getParameter("dataFine") ));
				request.setAttribute("listaPrenotabileByDateInizioAndFine", prenotabileDao.doRetrieveByDataInizioAndDataFine(request.getParameter("dataInizio"), request.getParameter("dataFine") ));

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e l account, ma non la data di fine e il numero capsula
		if(!vuota(dataInizio) && !vuota(account) && vuota( dataFine) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("prenotazioneByDateInizioAndAccount", pDao.doRetrivePrenotazioniByDataInizioAndAccount(dataInizio,account ));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di fine e l account
		if(!vuota(dataFine) && !vuota(account) && vuota(dataInizio) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("prenotazioneByDateFineAndAccount", pDao.doRetrieveByDataFineAndAccount(dataFine, account));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce l account e il numero capsula, ma  non la data di fine e la data di inizio
		if( !vuota( account) && !vuota( numeroCapsula) && vuota( dataFine) && vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				request.setAttribute("prenotazioneByNumeroCapsulaAndAccount", pDao.doRetrivePrenotazioniByNumeroCapsulaAndAccount(numeroCapsulaSelected, account  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		
		//inserisce tutto
		if( !vuota( account) && !vuota( numeroCapsula) && !vuota( dataFine) && !vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				request.setAttribute("prenotazioneByAll", pDao.doRetrivePrenotazioniByAll(numeroCapsulaSelected, account, dataInizio, dataFine  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce capsula,data inizio e account
		if( !vuota( account) && !vuota( numeroCapsula) && vuota(dataFine) && !vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				request.setAttribute("prenotazioneByAccountAndIdAndDataInizio", pDao.doRetrivePrenotazioniByAccountAndIdAndDataInizio(numeroCapsulaSelected, account, dataInizio));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce capsula,data fine e account
		if( !vuota( account) && !vuota( numeroCapsula) && !vuota(dataFine) && vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				request.setAttribute("prenotazioneByAccountAndIdAndDataFine", pDao.doRetrivePrenotazioniByAccountAndIdAndDataFine(numeroCapsulaSelected, account, dataFine));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		
		
		
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestorePrenotazioni/VisualizzaPrenotazioniGestore.jsp");
	    dispatcher.forward(request, response);
			
		
	}
	
	private boolean vuota(String checkString) {
		
		//Se c'è scritto qualcosa
		if(checkString!=null && checkString!="" && !checkString.isEmpty()) {
			return false;
		}
		
		//se è vuota vero
		return true;
	}

}
