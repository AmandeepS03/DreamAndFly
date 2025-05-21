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
 * Servlet implementation class VisualizzaPrenotazioniGestoreServlet
 */
@WebServlet("/VisualizzaPrenotazioniGestoreServlet")
public class VisualizzaPrenotazioniGestoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaPrenotazioniGestoreServlet() {
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
		try {
			request.setAttribute("listaUtentiPrenotati", pDao.doRetrieveEmailWithPrenotazione());
			
			
			
			
			//inserisce il numero capsule e vuole le fasce orarie prenotabili
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestorePrenotazioni/VisualizzaPrenotazioniGestore.jsp");
	    dispatcher.forward(request, response);
	}
	

}
