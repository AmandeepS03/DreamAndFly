package applicationLogic.utenteManagement;

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

import storage.AccountUser;
import storage.AccountUserDao;



/**
 * Servlet implementation class EmailDisponibilityJSON
 */

@WebServlet("/EmailDisponibility")
public class EmailDisponibility extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private static Logger logger = Logger.getAnonymousLogger();
  
   
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ParseException {
    
      DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
      AccountUserDao dao = new AccountUserDao(ds);
      AccountUser user = null;
            
      response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
       
        
        if (email != null && !email.equals("")) {
        	user = dao.doRetrieveByKey(email);
        }     
        
        String risultato = null;
        if (user != null && user.getEmail()!=null) {
            risultato = "email già registrata";
        } else {
            risultato = " ";
        }
        out.println("<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>");
        out.println("<response>");
        out.println("<functionName>handleEmail</functionName>");
        out.println("<result>" + risultato + "</result>");
        out.println("</response>");
    }
    
   

   
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | IOException | ParseException e) {
            logger.log(Level.WARNING, "Problema accesso DB!", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            response.setContentType("text/plain");
            response.getWriter().write("Errore durante l'accesso al database.");
        }
    }
    
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | IOException | ParseException e) {
            logger.log(Level.WARNING, "Problema accesso DB!", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            response.setContentType("text/plain");
            response.getWriter().write("Errore durante l'accesso al database.");
        }
    }
    

}