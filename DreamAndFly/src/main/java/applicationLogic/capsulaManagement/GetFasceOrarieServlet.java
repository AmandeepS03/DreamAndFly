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

import storage.FasciaOrariaDao;

/**
 * Servlet implementation class GetFasceOrarieServlet
 */
@WebServlet("/GetFasceOrarieServlet")
public class GetFasceOrarieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFasceOrarieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		FasciaOrariaDao tool=new FasciaOrariaDao(ds);
		Integer page = (Integer) request.getAttribute("page");
		
		
		try {
			request.setAttribute("listaFasceOrarie", tool.doRetriveAll() );
			
			
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
		}
		

			
		if(page!=null && page==2) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ModificaDisponibilità.jsp");
		    dispatcher.forward(request, response);
		}else if(page!=null && page==3) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/ProlungaDisponibilità.jsp");
		    dispatcher.forward(request, response);
		}else if(page!=null && page==4) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/GestoreGUI/GestoreCapsule/RegistraCapsula.jsp");
		    dispatcher.forward(request, response);
		}else if(page!=null && page==5) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/RicercaDisponibilitàGUI/RicercaDisponibilità.jsp");
		    dispatcher.forward(request, response);
		}else if(page!=null && page==6) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/RicercaDisponibilitàGUI/CapsuleDisponibili.jsp");
		    dispatcher.forward(request, response);
		}
	}
	

}
