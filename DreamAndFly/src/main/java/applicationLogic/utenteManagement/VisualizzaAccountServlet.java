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

import storage.AccountUserDao;

/**
 * Servlet implementation class VisualizzaAccountServlet
 */
@WebServlet("/VisualizzaAccountServlet")
public class VisualizzaAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();
    
    public VisualizzaAccountServlet() {
        super();
        
    }

	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountUserDao tool=new AccountUserDao(ds);
		
		try {
			request.setAttribute("listaUtenti", tool.doRetrieveAll() );
			request.setAttribute("listaUtentiPerSelect", tool.doRetrieveAll() );
			
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreAccount/Visualizza-EliminaAccount.jsp");
	    dispatcher.forward(request, response);
	}



	}
