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
		
		//inserisce il numero capsula 
		
		  if(request.getParameter("numeroCapsula")!=null ) {
			  
			  Integer numeroCapsulaSelect = Integer.parseInt(request.getParameter("numeroCapsula")); 
			  System.out.println("numeroCapsulaSelect: "+numeroCapsulaSelect);
				  try {
					request.setAttribute("listaPrenotazioneByCapsula", pDao.doRetrivePrenotazioniByNumeroCapsulaAll(numeroCapsulaSelect));
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			  
		  }
		 
		
		//insersce l'account
		if(request.getParameter("account")!=null && request.getParameter("account")!="") {
			try {
				request.setAttribute("listaPrenotazioneByAccount", pDao.doRetrivePrenotazioniByAccount(request.getParameter("account")));
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
		
		//inserisce la data di inizio
		if(request.getParameter("dataInizio")!=null && request.getParameter("dataInizio")!="" && request.getParameter("dataFine")=="" &&  request.getParameter("dataFine")==null) {
			try {
				System.out.println("dataInizio: "+request.getParameter("dataInizio"));
				request.setAttribute("listaPrenotazioneByDate", pDao.doRetrivePrenotazioniByDataInizio(request.getParameter("dataInizio")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//inserisce la data di fine
		if(request.getParameter("dataFine")!=null && request.getParameter("dataFine")!="" && request.getParameter("dataInizio")==null && request.getParameter("dataInizio")=="") {
			try {
				request.setAttribute("listaPrenotazioneByDateFine", pDao.doRetrivePrenotazioniByDataFine(request.getParameter("dataFine")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e la data di fine
		if(request.getParameter("dataInizio")!= null && request.getParameter("dataInizio")!="" && request.getParameter("dataFine")!=null && request.getParameter("dataFine")!="") {
			try {
				System.out.println("Date: "+request.getParameter("dataInizio")+ "  " + request.getParameter("dataFine"));
				request.setAttribute("listaPrenotazioneByDateInizioAndFine", pDao.doRetrieveByDataInizioAndDataFine(request.getParameter("dataInizio"), request.getParameter("dataFine") ));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//inserisce la data di inizio e l account
		if(request.getParameter("dataInizio")!= null && request.getParameter("dataInizio")!="" && request.getParameter("account")!=null && request.getParameter("account")!="") {
			try {
				request.setAttribute("prenotazioneByDateInizioAndAccount", pDao.doRetrivePrenotazioniByDataInizioAndAccount(request.getParameter("dataInizio"),request.getParameter("account") ));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestorePrenotazioni/VisualizzaPrenotazioniGestore.jsp");
	    dispatcher.forward(request, response);
			
		
	}

}
