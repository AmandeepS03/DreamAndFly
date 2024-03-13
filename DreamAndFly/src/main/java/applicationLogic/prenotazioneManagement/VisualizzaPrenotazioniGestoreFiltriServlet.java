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
		
		String numeroCapsula = request.getParameter("numeroCapsula");
		String account = request.getParameter("account");
		String dataInizio = request.getParameter("dataInizio");
		String dataFine = request.getParameter("dataFine");
		
		
		//inserisce solo il numero capsula 
		
		  if(!vuota(numeroCapsula) && vuota( account) && vuota( dataInizio) && vuota( dataFine) ) {
			  
			  Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula")); 
				  try {
					request.setAttribute("listaPrenotazioneByCapsula", pDao.doRetrievePrenotazioniByNumeroCapsulaAll(numeroCapsulaSelect));
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			  
		  }
		 
		
		//insersce solo l'account
		if(!vuota( account) && vuota( dataInizio) && vuota( dataFine) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByAccount", pDao.doRetrievePrenotazioniByAccount(request.getParameter("account")));
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
		
		//inserisce solo la data di inizio
		if(!vuota(dataInizio) && vuota( dataFine) && vuota( account) && vuota( numeroCapsula)) {
			try {
				System.out.println("dataInizio: "+request.getParameter("dataInizio"));
				request.setAttribute("listaPrenotazioneByDate", pDao.doRetrievePrenotazioniByDataInizio(request.getParameter("dataInizio")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//inserisce solo la data di fine
		if(!vuota( dataFine) &&  vuota( dataInizio) && vuota( account) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByDateFine", pDao.doRetrievePrenotazioniByDataFine(request.getParameter("dataFine")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e la data di fine, ma non il numero capsula e l account 
		if(!vuota(dataInizio) && !vuota(dataFine) && vuota( account) && vuota( numeroCapsula)) {
			try {
				System.out.println("Date: "+request.getParameter("dataInizio")+ "  " + request.getParameter("dataFine"));
				request.setAttribute("listaPrenotazioneByDateInizioAndFine", pDao.doRetrievePrenotazioniByDataInizioAndFine(request.getParameter("dataInizio"), request.getParameter("dataFine") ));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e l account, ma non la data di fine e il numero capsula
		if(!vuota(dataInizio) && !vuota(account) && vuota( dataFine) && vuota( numeroCapsula)) {
			try {
				request.setAttribute("prenotazioneByDateInizioAndAccount", pDao.doRetrievePrenotazioniByDataInizioAndAccount(dataInizio,account ));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e il numero capsula, ma non la data di fine e l account
		if(!vuota(dataInizio) && vuota(account) && vuota( dataFine) && !vuota( numeroCapsula)) {
			Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				request.setAttribute("prenotazioneByDateInizioAndNumeroCapsula", pDao.doRetrieveByPrenotazioneByDateInizioAndNumeroCapsula(dataInizio,numeroCapsulaSelect ));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce l account e il numero capsula, ma  non la data di fine e la data di inizio
		if( !vuota( account) && !vuota( numeroCapsula) && vuota( dataFine) && vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				
				request.setAttribute("prenotazioneByNumeroCapsulaAndAccount", pDao.doRetrievePrenotazioniByNumeroCapsulaAndAccount(numeroCapsulaSelected, account  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		
		//inserisce numero capsula e data fine, ma non l account e la data inizio
		if( vuota( account) && !vuota( numeroCapsula) && !vuota( dataFine) && vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				
				request.setAttribute("prenotazioneByNumeroCapsulaAndDataFine", pDao.doRetrievePrenotazioniByNumeroCapsulaAndDataFine(numeroCapsulaSelected, dataFine  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce numero capsula, data inizio e data fine, ma non account.
		if( vuota( account) && !vuota( numeroCapsula) && !vuota( dataFine) && !vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				
				request.setAttribute("prenotazioneByNumeroCapsulaAndDataInizioAndDataFine", pDao.doRetrievePrenotazioneByNumeroCapsulaAndDataInizioAndDataFine(numeroCapsulaSelected,dataInizio, dataFine  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce numero capsula,account e data inizio ma non data fine
		if( !vuota( account) && !vuota( numeroCapsula) && vuota( dataFine) && !vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				
				request.setAttribute("prenotazioneByNumeroCapsulaAndDataInizioAndAccount", pDao.doRetrievePrenotazioneByNumeroCapsulaAndDataInizioAndAccount(numeroCapsulaSelected,dataInizio, account  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce numero capsula, data fine e account, ma non data inizio
		if( !vuota( account) && !vuota( numeroCapsula) && !vuota( dataFine) && vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				
				request.setAttribute("prenotazioneByNumeroCapsulaAndDataFineAndAccount", pDao.doRetrievePrenotazioneByNumeroCapsulaAndDataFineAndAccount(numeroCapsulaSelected,dataFine, account  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce tutto
		if( !vuota( account) && !vuota( numeroCapsula) && !vuota( dataFine) && !vuota( dataInizio)) {
			
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				
				request.setAttribute("prenotazioneByAll", pDao.doRetrievePrenotazioneByAll(numeroCapsulaSelected,dataInizio,dataFine, account  ));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce data inizio, data fine e account, ma non il numero capsula
		if( !vuota( account) && vuota( numeroCapsula) && !vuota( dataFine) && !vuota( dataInizio)) {
					
			
			try {
						
				request.setAttribute("prenotazioneByDataInizioAndDataFineAndAccount", pDao.doRetrievePrenotazioniByDataInizioAndDataFineAndAccount(dataInizio,dataFine, account ));
			} catch (SQLException e) {
				e.printStackTrace();
				}

		}
		
		//inserisce  data fine e account, ma non il numero capsula e la data inizio
		if( !vuota( account) && vuota( numeroCapsula) && !vuota( dataFine) && vuota( dataInizio)) {

			try {

				request.setAttribute("prenotazioneByDataFineAndAccount", pDao.doRetrievePrenotazioneByDataFineAndAccount(dataFine, account));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		//inserisce  data fine e account, ma non il numero capsula e la data inizio
		if (vuota(account) && vuota(numeroCapsula) && vuota(dataFine) && vuota(dataInizio)) {
			try {

				request.setAttribute("tutteLePrenotazioni",
						pDao.doRetriveAll());
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
