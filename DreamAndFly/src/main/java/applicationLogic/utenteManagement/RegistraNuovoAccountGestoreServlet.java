package applicationLogic.utenteManagement;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.AccountUser;
import storage.AccountUserDao;
import utils.HelperClass;

/**
 * Servlet implementation class RegistraNuovoAccountGestoreServlet
 */
@WebServlet("/RegistraNuovoAccountGestoreServlet")
public class RegistraNuovoAccountGestoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistraNuovoAccountGestoreServlet() {
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
		//passa i dati e chiama doSaveGestore
		String email = request.getParameter("email");
	    String nome = HelperClass.filter(request.getParameter("nome"));
	    String cognome = HelperClass.filter(request.getParameter("cognome"));
	    String passwd = HelperClass.filter(request.getParameter("password"));
	    Integer ruolo = Integer.parseInt(request.getParameter("ruolo"));
	    String cellulare = HelperClass.filter(request.getParameter("cellulare"));
	    
	    AccountUser user = new AccountUser();
	    user.setEmail(email);
	    user.setName(nome);
	    user.setSurname(cognome);
	    user.setPassword(HelperClass.toHash(passwd));
	    user.setRuolo(ruolo);
	    user.setNumber(cellulare);
	    
	    AccountUserDao userdao=null;		      
	      DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	      userdao = new AccountUserDao(ds);
	      try {
			userdao.doSaveGestore(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreAccount/Visualizza-EliminaAccount.jsp");
	      dispatcher.forward(request, response);
	}

}
