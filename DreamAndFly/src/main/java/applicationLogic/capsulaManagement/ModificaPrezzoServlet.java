package applicationLogic.capsulaManagement;

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

import storage.CapsulaDao;

/**
 * Servlet implementation class ModificaPrezzoServlet
 */
@WebServlet("/ModificaPrezzoServlet")
public class ModificaPrezzoServlet extends HttpServlet {
	private static Logger logger = Logger.getAnonymousLogger();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaPrezzoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CapsulaDao tool=new CapsulaDao(ds);
		Integer id = Integer.parseInt(request.getParameter("numero"));
		Float prezzo = Float.valueOf(request.getParameter("prezzo"));
		
		try {
			tool.doUpdatePrezzoOrario(id, prezzo);
			
			
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
		
		request.setAttribute("showPopup", true);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ModificaPrezzo.jsp");
	    dispatcher.forward(request, response);
		
	}	}


