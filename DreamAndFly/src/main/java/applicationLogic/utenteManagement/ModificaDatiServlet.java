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
import storage.AccountUserDao;
import utils.HelperClass;

/**
 * Servlet implementation class ModificaDatiServlet
 */
@WebServlet("/ModificaDatiServlet")
public class ModificaDatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();
	private static final String ERROR = "Problema Sql!";
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountUser auth= new AccountUser();
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountUserDao user = new AccountUserDao(ds);
		String email = "";
		//se sto reimpostando da password dimenticata
		String pageModifica = (String) request.getSession().getAttribute("pageModifica");
		if(pageModifica!= null && pageModifica.equals("1")) {
			email = (String) request.getSession().getAttribute("email");
		}else {
			auth = (AccountUser) request.getSession().getAttribute("auth");
			email = auth.getEmail();
		}
		String passwordNuova=null;
		String confermaPassword=null;
		String cellulare = null;
		

		
		
	 	   if(request.getParameter("password")!= "") {
			passwordNuova= request.getParameter("password");
			confermaPassword = request.getParameter("ConfermaPassword");		
		
			if(!passwordNuova.equals(confermaPassword)) { 
		      String error ="Password e conferma password non corrispondono";
		      request.setAttribute("error", error); 
		      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/UtenteRegistratoGUI/AreaUtente.jsp");
		      dispatcher.forward(request, response);}
		
		 passwordNuova= HelperClass.toHash(passwordNuova);
		 try {
			user.doUpdatePassword(email,passwordNuova);
		} catch (SQLException e) {
			logger.log(Level.WARNING, ERROR,e);
		}
	 	   }
	 	  if(pageModifica!= null && pageModifica.equals("1")) {
			  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/AutenticazioneGUI/Login.jsp");
			  dispatcher.forward(request, response); 
		  }
		
		  if(request.getParameter("cellulare")!="") {
			  cellulare=request.getParameter("cellulare");
			  try {
				user.doUpdateNumber(email, cellulare);
			} catch (SQLException e) {
				logger.log(Level.WARNING, ERROR,e);
			}
			  auth.setNumber(cellulare);
		  }
		  
		 
		  
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/UtenteRegistratoGUI/AreaUtente.jsp");
		  dispatcher.forward(request, response); 
		
		}
}
