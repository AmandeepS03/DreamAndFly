package applicationLogic.capsulaManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.Prenotabile;
import storage.PrenotabileDao;

/**
 * Servlet implementation class ProlungaDisponibilitaServlet
 */
@WebServlet("/ProlungaDisponibilitaServlet")
public class ProlungaDisponibilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProlungaDisponibilitaServlet() {
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
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		PrenotabileDao tool=new PrenotabileDao(ds);
		Prenotabile prenotabile = new Prenotabile();
		Prenotabile util = new Prenotabile();
		String data = request.getParameter("data");		
		Integer id = Integer.valueOf(request.getParameter("numero"));
		Integer fascia = Integer.valueOf(request.getParameter("fasciaOraria"));
		
		
		try {
			prenotabile=tool.doRetrieveLastDateById(id);
			
			
		}catch (SQLException e){
			logger.log(Level.WARNING, "Problema Sql!",e);
	}
		
		LocalDate dataUltima = LocalDate.parse(prenotabile.getDataPrenotabile());
		LocalDate dataFinale = LocalDate.parse(data);
		int fasciaUltima = prenotabile.getFasciaOrariaNumero()+1;

		for(;dataUltima.isBefore(dataFinale)|| dataUltima.isEqual(dataFinale); dataUltima = dataUltima.plusDays(1)) {
			if(dataUltima.isEqual(dataFinale)) {
				for(;fasciaUltima<= fascia; fasciaUltima++) {
					util.setCapsulaId(id);
					util.setDataPrenotabile(dataUltima.toString());
					util.setFasciaOrariaNumero(fasciaUltima);
					try {
						tool.doSave(util);
					} catch (SQLException e) {
						e.printStackTrace();
					}						
			}
			}else {
			for(;fasciaUltima<= 24; fasciaUltima++) {
				util.setCapsulaId(id);
				util.setDataPrenotabile(dataUltima.toString());
				util.setFasciaOrariaNumero(fasciaUltima);
				try {
					tool.doSave(util);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			}
			fasciaUltima=1;
		}
		
	

}
}
