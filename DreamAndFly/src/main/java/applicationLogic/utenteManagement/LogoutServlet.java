package applicationLogic.utenteManagement;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out= response.getWriter()){
			if(request.getSession().getAttribute("auth") != null) {
				request.getSession().removeAttribute("auth");
				request.getSession().invalidate();
				RequestDispatcher dispatcher =
			              getServletContext().getRequestDispatcher("/Interface/Homepage.jsp");
	            dispatcher.forward(request, response);
				
				
			}else {
				RequestDispatcher dispatcher =
						 getServletContext().getRequestDispatcher("/Interface/Homepage.jsp");
			    dispatcher.forward(request, response);
				
			}
		}
	}
}
	
