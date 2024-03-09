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
		
		  if(request.getParameter("numeroCapsula")!=null && request.getParameter("numeroCapsula")!="" && !checked(request, account) && !checked(request, dataInizio) && !checked(request, dataFine) ) {
			  
			  Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula")); 
			  System.out.println("numeroCapsulaSelect: "+numeroCapsulaSelect);
				  try {
					request.setAttribute("listaPrenotazioneByCapsula", pDao.doRetrivePrenotazioniByNumeroCapsulaAll(numeroCapsulaSelect));
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			  
		  }
		 
		
		//insersce solo l'account
		if(request.getParameter("account")!=null && request.getParameter("account")!="" && !checked(request, dataInizio) && !checked(request, dataFine) && !checked(request, numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByAccount", pDao.doRetrivePrenotazioniByAccount(request.getParameter("account")));
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
		
		//inserisce solo la data di inizio
		if(request.getParameter("dataInizio")!=null && request.getParameter("dataInizio")!="" && request.getParameter("dataFine")=="" &&  request.getParameter("dataFine")==null && !checked(request, account) && !checked(request, numeroCapsula)) {
			try {
				System.out.println("dataInizio: "+request.getParameter("dataInizio"));
				request.setAttribute("listaPrenotazioneByDate", pDao.doRetrivePrenotazioniByDataInizio(request.getParameter("dataInizio")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//inserisce solo la data di fine
		if(request.getParameter("dataFine")!=null && request.getParameter("dataFine")!="" && request.getParameter("dataInizio")==null && request.getParameter("dataInizio")=="" && !checked(request, account) && !checked(request, numeroCapsula)) {
			try {
				request.setAttribute("listaPrenotazioneByDateFine", pDao.doRetrivePrenotazioniByDataFine(request.getParameter("dataFine")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e la data di fine, ma non il numero capsula e l account TODO
		if(request.getParameter("dataInizio")!= null && request.getParameter("dataInizio")!="" && request.getParameter("dataFine")!=null && request.getParameter("dataFine")!="" && !checked(request, account) && !checked(request, numeroCapsula)) {
			try {
				System.out.println("Date: "+request.getParameter("dataInizio")+ "  " + request.getParameter("dataFine"));
				request.setAttribute("listaPrenotazioneByDateInizioAndFine", pDao.doRetrieveByDataInizioAndDataFine(request.getParameter("dataInizio"), request.getParameter("dataFine") ));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e l account, ma non la data di fine e il numero capsula TODO
		if(request.getParameter("dataInizio")!= null && request.getParameter("dataInizio")!="" && request.getParameter("account")!=null && request.getParameter("account")!="" && !checked(request, dataFine) && !checked(request, numeroCapsula)) {
			try {
				request.setAttribute("prenotazioneByDateInizioAndAccount", pDao.doRetrivePrenotazioniByDataInizioAndAccount(request.getParameter("dataInizio"),request.getParameter("account") ));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//inserisce l account e il numero capsula, ma  non la data di fine e la data di inizio
		if(request.getParameter("account")!=null && request.getParameter("account")!="" && request.getParameter("numeroCapsula")!=null && request.getParameter("numeroCapsula")!="" && !checked(request, dataFine) && !checked(request, dataInizio)) {
			Integer numeroCapsulaSelected = Integer.parseInt(request.getParameter("numeroCapsula")); 
			try {
				String account2 = (String) request.getAttribute("account");
				request.setAttribute("prenotazioneByNumeroCapsulaAndAccount", pDao.doRetrivePrenotazioniByNumeroCapsulaAndAccount(numeroCapsulaSelected, account2  ));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		
		
		
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestorePrenotazioni/VisualizzaPrenotazioniGestore.jsp");
	    dispatcher.forward(request, response);
			
		
	}
	
	private boolean checked(HttpServletRequest request,String checkString) {
		
		//Se c'è scritto qualcosa
		if(request.getParameter(checkString)!=null && request.getParameter(checkString)!="" ) {
			return true;
		}
		
		//se non c'è nulla falso
		return false;
	}

}
