package applicationLogic.utenteManagement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InserisciCodiceServlet
 */
@WebServlet("/InserisciCodiceServlet")
public class InserisciCodiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserisciCodiceServlet() {
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
		String codiceInviato = (String) request.getSession().getAttribute("codicePassword");
		String codiceInserito = request.getParameter("codice");
		
		if(!codiceInviato.equals(codiceInserito)) {
			String error="codice errato";
			HttpSession session = request.getSession();
			session.setAttribute("isLogged", "false");
			request.setAttribute("error", error);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/AutenticazioneGUI/InserisciCodice.jsp");
			dispatcher.forward(request, response);
			return;

	
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/AutenticazioneGUI/ReimpostaPassword.jsp");
		dispatcher.forward(request, response);
		
	}

}
