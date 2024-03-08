package applicationLogic.prenotazioneManagement;

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

import storage.AccountUserDao;
import storage.PrenotazioneDao;

/**
 * Servlet implementation class EliminaPrenotazioneServlet
 */
@WebServlet("/EliminaPrenotazioneServlet")
public class EliminaPrenotazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaPrenotazioneServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		PrenotazioneDao tool=new PrenotazioneDao(ds);
		String codicePrenotazione = request.getParameter("codicePrenotazione");
		Integer codiceDiAccesso = Integer.parseInt(codicePrenotazione);

		try {
		
			tool.deletePrenotazione(codiceDiAccesso);
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/UtenteRegistratoGUI/LeMiePrenotazioni.jsp");
	    dispatcher.forward(request, response);
	}

}
