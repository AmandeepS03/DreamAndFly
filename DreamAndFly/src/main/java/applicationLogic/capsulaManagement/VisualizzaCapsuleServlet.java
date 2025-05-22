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
 * Servlet implementation class VisualizzaCapsule
 */
@WebServlet("/VisualizzaCapsuleServlet")
public class VisualizzaCapsuleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzaCapsuleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CapsulaDao tool=new CapsulaDao(ds);
		Integer page = (Integer) request.getAttribute("page");
		
		try {
			request.setAttribute("listaCapsule", tool.doRetrieveAll() );
			
			
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
		
		if(page!=null && page==0) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/VisualizzaCapsule.jsp");
			dispatcher.forward(request, response);
	    
		}else if(page!=null && page==1) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ModificaPrezzo.jsp");
		    dispatcher.forward(request, response);
			
		}else if(page!=null && page==2) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ModificaDisponibilità.jsp");
		    dispatcher.forward(request, response);
		}else if(page!=null && page==3) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ProlungaDisponibilità.jsp");
			dispatcher.forward(request, response);
		}else if(page!=null && page==4) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/RegistraCapsula.jsp");
		    dispatcher.forward(request, response);
		}else if (page!=null && page==5) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestorePrenotazioni/VisualizzaPrenotazioniGestore.jsp");
		    dispatcher.forward(request, response);
		}
	}

}
