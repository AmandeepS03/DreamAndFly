package applicationLogic.capsulaManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.Prenotabile;
import storage.PrenotabileDao;

/**
 * Servlet implementation class RicercaDisponibilitàServlet
 */
@WebServlet("/RicercaDisponibilitàServlet")
public class RicercaDisponibilitàServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LocalDate dataInizio;
	LocalDate dataFine;
	LocalDate data;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaDisponibilitàServlet() {
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
		PrenotabileDao tool= new PrenotabileDao(ds);
		Prenotabile prenotabile = new Prenotabile();
		String dataInizioStringa = request.getParameter("dal");	
		String dataFineStringa = request.getParameter("al");
		Integer orarioInizio = Integer.valueOf(request.getParameter("orarioInizio"));
		Integer orarioFine = Integer.valueOf(request.getParameter("orarioFine"));
		
		dataInizio = LocalDate.parse(dataInizioStringa);
		dataFine = LocalDate.parse(dataFineStringa);
		
		System.out.println("DataInizio: "+ dataInizioStringa);
		System.out.println("DataFine: "+ dataFineStringa);
		System.out.println("OrarioInizio: "+ orarioInizio);
		System.out.println("OrarioFine: "+ orarioFine);
		
		Collection<Integer> idList = new ArrayList<>() ;
		
		try {
			idList = tool.doRetrieveByDataInizioDataFine(dataInizioStringa, dataFineStringa);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		for(Integer i:idList) {
			System.out.println("Capsula id: "+ i);
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/RicercaDisponibilitàGUI/RicercaDisponibilità.jsp");
	    dispatcher.forward(request, response);


	}
	
//private boolean checkDate(int id) {
//	for(data=dataInizio;data.isBefore(dataFine)|| data.isEqual(dataFine);data = data.plusDays(1) ) {
//		
//	}
//}

}
