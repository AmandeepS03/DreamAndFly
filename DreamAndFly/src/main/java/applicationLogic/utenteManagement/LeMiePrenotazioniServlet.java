package applicationLogic.utenteManagement;

import java.io.IOException;
import java.sql.SQLException;
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
import storage.PrenotazioneDao;

/**
 * Servlet implementation class LeMiePrenotazioniServlet
 */
@WebServlet("/LeMiePrenotazioniServlet")
public class LeMiePrenotazioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeMiePrenotazioniServlet() {
        super();
        
    }

	/**	
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		PrenotazioneDao pDAO = new PrenotazioneDao(ds);
		
		AccountUser auth=(AccountUser) request.getSession().getAttribute("auth");
		String email=auth.getEmail();
		
		try {
			request.setAttribute("prenotazioni", pDAO.doRetrieveByEmail(email,auth.getRuolo()));
		}catch (SQLException e) {
			logger.log(Level.WARNING, "Errore SQL", e);
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Interface/UtenteRegistratoGUI/LeMiePrenotazioni.jsp");
		dispatcher.forward(request, response);
	}

}
