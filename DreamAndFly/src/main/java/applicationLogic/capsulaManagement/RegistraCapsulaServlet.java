package applicationLogic.capsulaManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.Prenotabile;
import storage.PrenotabileDao;
import storage.Capsula;
import storage.CapsulaDao;

/**
 * Servlet implementation class RegistraCapsulaServlet
 */
@WebServlet("/RegistraCapsulaServlet")
public class RegistraCapsulaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();

	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistraCapsulaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CapsulaDao tool = new CapsulaDao(ds);
		Capsula capsula = new Capsula();
		PrenotabileDao toolP= new PrenotabileDao(ds);
		Prenotabile prenotabile = new Prenotabile();
		
		Integer id = Integer.valueOf(request.getParameter("numero"));
		Float prezzo = Float.valueOf(request.getParameter("prezzoOrario"));
		String tipologia = request.getParameter("tipologia");
		
		String dataInizioStringa = request.getParameter("dal");	
		String dataFineStringa = request.getParameter("al");
		Integer orarioInizio = Integer.valueOf(request.getParameter("orarioInizio"));
		Integer orarioFine = Integer.valueOf(request.getParameter("orarioFine"));
		
		LocalDate dataInizio = LocalDate.parse(dataInizioStringa);
		LocalDate dataFine = LocalDate.parse(dataFineStringa);
		
		capsula.setId(id);
		capsula.setPrezzo_orario(prezzo);
		capsula.setTipologia(tipologia);
		
		try {
			tool.doSave(capsula);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		for(;dataInizio.isBefore(dataFine)|| dataInizio.isEqual(dataFine); dataInizio = dataInizio.plusDays(1)) {
			if(dataInizio.isEqual(dataFine)) {
				for(;orarioInizio<= orarioFine; orarioInizio++) {
					prenotabile.setCapsulaId(id);
					prenotabile.setDataPrenotabile(dataInizio.toString());
					prenotabile.setFasciaOrariaNumero(orarioInizio);
					try {
						
						toolP.doSave(prenotabile);
						
					} catch (SQLException e) {
						e.printStackTrace();
				}						
			}
			}else {
			for(;orarioInizio<= 24; orarioInizio++) {
				prenotabile.setCapsulaId(id);
				prenotabile.setDataPrenotabile(dataInizio.toString());
				prenotabile.setFasciaOrariaNumero(orarioInizio);
				try {
					
					toolP.doSave(prenotabile);
				
				} catch (SQLException e) {
					e.printStackTrace();
			}
			}
			orarioInizio=1;
		}
		}


		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/RegistraCapsula.jsp");
	    dispatcher.forward(request, response);
}
	}


