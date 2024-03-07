package applicationLogic.capsulaManagement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		 String prezzo = (String) request.getParameter("prezzo") ;
		 request.setAttribute("prezzo", prezzo);
		 
		 System.out.println("Prezzo: "+prezzo);
		 System.out.println("dataInizio: "+dataInizioStringa);
		 
		 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/UtenteRegistratoGUI/Pagamento.jsp");
		    dispatcher.forward(request, response);

		   
	
	}

}
