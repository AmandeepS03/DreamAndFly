package applicationLogic.capsulaManagement;

import java.io.IOException;

import java.sql.Date;
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

import storage.PrenotabileDao;
import storage.Prenotabile;


/**
 * Servlet implementation class ModificaDisponibilitàServlet
 */
@WebServlet("/ModificaDisponibilitaServlet")
public class ModificaDisponibilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaDisponibilitaServlet() {
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
		PrenotabileDao tool=new PrenotabileDao(ds);
		Prenotabile prenotabile = new Prenotabile();
//		Date data = Date.valueOf(request.getParameter("data"));
		String data = request.getParameter("data");		
		Integer id = Integer.valueOf(request.getParameter("numero"));
		Integer fascia = Integer.valueOf(request.getParameter("fasciaOraria"));
		String disponibileValue = request.getParameter("disponibile");

		
		if(disponibileValue.equals("no")) {
		try {
			tool.doDelete(data,id,fascia);
			
			
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
		}else if(disponibileValue.equals("si")) {
			try {
				prenotabile.setDataPrenotabile(data);
				prenotabile.setCapsulaId(id);
				prenotabile.setFasciaOrariaNumero(fascia);
				tool.doSave(prenotabile);
				
				
			}catch (SQLException e){
				logger.log(Level.WARNING, "Problema Sql!",e);
			}
			}
		

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ModificaDisponibilità.jsp");
		    dispatcher.forward(request, response);
		
	}
	

}
