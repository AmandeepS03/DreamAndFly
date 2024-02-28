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
 * Servlet implementation class RicercaAccountServlet
 */
@WebServlet("/RicercaAccountServlet")
public class RicercaAccountServlet extends HttpServlet {
	private static Logger logger = Logger.getAnonymousLogger();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountUserDao tool=new AccountUserDao(ds);
		String email = (String)request.getParameter("utente");
		if(!email.equals("all")) {
		try {
			request.setAttribute("type", "elemento");
			request.setAttribute("user", tool.doRetrieveByKey(email));
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
	
		
		
	}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreAccount/Visualizza-EliminaAccount.jsp");
	    dispatcher.forward(request, response);
	}

}
