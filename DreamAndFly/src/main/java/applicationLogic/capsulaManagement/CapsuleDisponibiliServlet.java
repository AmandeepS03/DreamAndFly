package applicationLogic.capsulaManagement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage.AccountUser;

/**
 * Servlet implementation class CapsuleDisponibiliServlet
 */
@WebServlet("/CapsuleDisponibiliServlet")
public class CapsuleDisponibiliServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CapsuleDisponibiliServlet() {
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
		String dataInizioStringa = (String) request.getAttribute("dataInizio") ;	
		String dataFineStringa = (String) request.getAttribute("dataFine") ;	

		 request.setAttribute("prezzo", request.getParameter("prezzo"));
		 request.setAttribute("dataInizio", request.getParameter("dataInizio"));
		 request.setAttribute("dataFine", request.getParameter("dataFine"));
		 request.setAttribute("orarioInizio", request.getParameter("orarioInizio"));
		 request.setAttribute("orarioFine", request.getParameter("orarioFine"));
		 request.setAttribute("capsulaId", request.getParameter("capsulaId"));

		  AccountUser user = (AccountUser) request.getSession().getAttribute("auth");	
	        
	        if(user==null) {
	        	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/AutenticazioneGUI/Login.jsp");
	        	dispatcher.forward(request, response);	
	        }

		 
		 
		
		 
		 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/UtenteRegistratoGUI/Pagamento.jsp");
		    dispatcher.forward(request, response);

		   
	
	}

}
