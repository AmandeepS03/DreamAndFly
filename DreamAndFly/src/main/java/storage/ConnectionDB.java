package storage;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ConnectionDB implements ServletContextListener{
	
	private static Logger logger = Logger.getAnonymousLogger();

	public void contextInitialized(ServletContextEvent event) {

		ServletContext context = event.getServletContext();
		DataSource ds = null;

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/dreamandfly");
		} catch(NamingException e) {
			logger.log(Level.WARNING, "Errore SQL", e);
		}

		context.setAttribute("DataSource", ds);
		logger.log(Level.WARNING, "Creating DataSource: {0}", ds);
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

		DataSource ds = (DataSource) context.getAttribute("DataSource");
		logger.log(Level.WARNING, "Deleting DataSource: {0}", ds);
	}
}