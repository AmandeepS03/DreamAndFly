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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import storage.AccountUser;
import storage.AccountUserDao;
import utils.HelperClass;

/**
 * Servlet implementation class InserisciEmailServlet
 */
@WebServlet("/InserisciEmailServlet")
public class InserisciEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserisciEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF=8");
		AccountUser user = null;
		String email= request.getParameter("email");

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountUserDao tool=new AccountUserDao(ds);

		try {
			user=tool.doRetrieveByKey(email);
		}catch (SQLException e) {
			logger.log(Level.WARNING, "Problema Parse/Sql!");
		}

		
		if(user.getEmail() == null) {
			String error="e-mail non esistente";
			HttpSession session = request.getSession();
			session.setAttribute("isLogged", "false");
			request.setAttribute("error", error);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/AutenticazioneGUI/InserisciEmail.jsp");
			dispatcher.forward(request, response);
			return;

	
		}
		
		//String codice = HelperClass.generateRandomString(6);
		String codice = "123456";
		request.getSession().setAttribute("codicePassword", codice);
		request.getSession().setAttribute("email", email);
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/AutenticazioneGUI/InserisciCodice.jsp");
		  dispatcher.forward(request, response);
		 


	}

}
