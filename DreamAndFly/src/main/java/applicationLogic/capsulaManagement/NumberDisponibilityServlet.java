package applicationLogic.capsulaManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.Capsula;
import storage.CapsulaDao;

/**
 * Servlet implementation class NumberDisponibilityServlet
 */
@WebServlet("/NumberDisponibilityServlet")
public class NumberDisponibilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getAnonymousLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NumberDisponibilityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws SQLException, IOException, ParseException {
    	    
    	      DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
    	      CapsulaDao dao = new CapsulaDao(ds);
    	      Capsula capsula = null;
    	            
    	      response.setContentType("text/xml");
    	        PrintWriter out = response.getWriter();
    	        String id = (String)request.getParameter("numero");
    	       
    	        
    	        if (id != null && !id.equals("")) {
    	        	capsula = dao.doRetrieveByKey(Integer.valueOf(id));
    	        }     
    	        
    	        String risultato = null;
    	        if (capsula != null && capsula.getId()!=null) {
    	            risultato = "numero capsula già esistente";
    	        } else {
    	            risultato = " ";
    	        }
    	        out.println("<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>");
    	        out.println("<response>");
    	        out.println("<functionName>handleNumber</functionName>");
    	        out.println("<result>" + risultato + "</result>");
    	        out.println("</response>");
    	    }
    	    
    	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	       
    	      try {
    	        processRequest(request, response);
    	      } catch (SQLException | IOException | ParseException e) {
    	        logger.log(Level.WARNING, "Problema accesso DB!");
    	      }
    	    
    	    
    	    }
    	    
    	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	        try {
    	      processRequest(request, response);
    	    } catch (IOException | SQLException | ParseException e) {
    	      logger.log(Level.WARNING, "Problema accesso DB!");
    	    }
    	    }
}
