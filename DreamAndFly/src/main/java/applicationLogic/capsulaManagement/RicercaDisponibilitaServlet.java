package applicationLogic.capsulaManagement;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import storage.Prenotabile;
import storage.PrenotabileDao;
import storage.Capsula;
import storage.CapsulaDao;
import storage.FasciaOraria;
import storage.FasciaOrariaDao;
/**
 * Servlet implementation class RicercaDisponibilitàServlet
 */
@WebServlet("/RicercaDisponibilitaServlet")
public class RicercaDisponibilitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LocalDate dataInizio;
	LocalDate dataFine;
	LocalDate data;
	PrenotabileDao tool;
	FasciaOrariaDao toolFasciaOraria ;
	Integer orarioFine;
	Integer orarioInizio;
	int orario;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaDisponibilitaServlet() {
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
		tool= new PrenotabileDao(ds);
		Prenotabile prenotabile = new Prenotabile();
		
		CapsulaDao toolC = new CapsulaDao(ds);
		List<Capsula> capsule = new ArrayList<Capsula>();
		
		String dataInizioStringa = request.getParameter("dal");	
		String dataFineStringa = request.getParameter("al");
		orarioInizio = Integer.valueOf(request.getParameter("orarioInizio"));
		orarioFine = Integer.valueOf(request.getParameter("orarioFine"));
		
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
		
		
		if (!dataInizio.isEqual(dataFine)) {
		    Collection<Integer> idsToRemove = new ArrayList<>();
		    for (Integer id : idList) {
		        if (!checkDate(id))
		            idsToRemove.add(id);
		    }
		    idList.removeAll(idsToRemove);
		} else {
		    Collection<Integer> idsToRemove = new ArrayList<>();
		    for (Integer id : idList) {
		        if (!checkOrario(id, orarioInizio, orarioFine, dataInizioStringa))
		            idsToRemove.add(id);
		    }
		    idList.removeAll(idsToRemove);
		}

		
		for(Integer id:idList) {
			try {
				capsule.add(toolC.doRetrieveByKey(id));
			} catch (SQLException e) {
				e.printStackTrace();
			}			
				
			
		}
		
		request.setAttribute("listaCapsule", capsule);
		request.setAttribute("counterOre", contaOre(dataInizio, dataFine, orarioInizio, orarioFine));
		request.setAttribute("dataInizio", dataInizio.toString());
		request.setAttribute("dataFine", dataFine.toString());
		
		try {
			
			toolFasciaOraria = new FasciaOrariaDao(ds);
			FasciaOraria fasciaOrariaInizio = toolFasciaOraria.doRetrieveByKey(orarioInizio);
			request.setAttribute("orarioInizio", fasciaOrariaInizio.getorarioInizio());
			
			FasciaOraria fasciaOrariaFine = toolFasciaOraria.doRetrieveByKey(orarioFine);
			request.setAttribute("orarioFine", fasciaOrariaFine.getorarioFine());
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		 
		
		System.out.println("Counter: " + contaOre(dataInizio, dataFine, orarioInizio, orarioFine));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Interface/RicercaDisponibilitàGUI/CapsuleDisponibili.jsp");
	    dispatcher.forward(request, response);


	}
	
private boolean checkDate(int id) {
	
	for(data=dataInizio;data.isBefore(dataFine)|| data.isEqual(dataFine);data = data.plusDays(1) ) {
		try {
			if(!(tool.doRetrieveByIdAndDate(id, data.toString())))
				return false;
			else {
				if(data.isEqual(dataInizio)) {
				  if(!checkOrario(id,orarioInizio,24, data.toString()))
					  return false;
				}
				else if (data.isEqual(dataFine)) {
				  if(!checkOrario(id,1,orarioFine, data.toString()))
					  return false;
				}
				else {
				  if(!checkOrario(id,1,24, data.toString()))
					  return false;
			}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return true;
}

private boolean checkOrario(int id,int orarioInizio, int orarioFine, String data) {

	for(orario = orarioInizio; orario<= orarioFine ;orario++ ) {
		try {
			if(!(tool.doRetrieveByIdAndFasciaOrariaAndDate(id,orario, data.toString())))
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	return true;
}

private int contaOre(LocalDate dataInizio, LocalDate dataFine, int orarioInizio, int orarioFine) {
	int counterOre =0;
	if(dataInizio.isEqual(dataFine))
		return (orarioFine-orarioInizio)+1;
	else {
		for(data=dataInizio;data.isBefore(dataFine)|| data.isEqual(dataFine);data = data.plusDays(1) ) {
		
			if(data.isEqual(dataInizio)) {
				for(orario = orarioInizio; orario<= 24 ;orario++ ) {
						counterOre++;
					}
			}else if (data.isEqual(dataFine)) {
				for(orario = 1; orario<= orarioFine ;orario++ ) {
					counterOre++;
				}
			}else{
				for(orario = 1; orario<= 24 ;orario++ ) {
					counterOre++;
				}	 
			}
		}
	}
	return counterOre;
		
	}
	
	



}
