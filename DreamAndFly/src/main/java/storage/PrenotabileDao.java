package storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class PrenotabileDao {
	private Collection<Prenotabile> prenotabilelist = null;
	public Collection<Prenotabile> getPrenotabilelist() {
		return prenotabilelist;
	}

	public void setPrenotabilelist(Collection<Prenotabile> prenotabilelist) {
		this.prenotabilelist = prenotabilelist;
	}

	private DataSource ds=null;
	
	private static final Logger logger = Logger.getLogger(PrenotabileDao.class.getName());

	public PrenotabileDao(DataSource ds) {
		super();
		this.ds=ds;
	}
	
	public Prenotabile doRetrieveDataPrenotabile(String dataPrenotabile) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		Prenotabile prenotabile=new Prenotabile();
		try {
			con=ds.getConnection();
			query = "select * from prenotabile where data_prenotabile = ? ";
			pst = con.prepareStatement(query);
			pst.setString(1, dataPrenotabile);
			rs = pst.executeQuery();

			if(rs.next()) {
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("caspula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));

			}

		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE , e.getMessage());
		} finally {
			try {
				if(pst != null)
					pst.close();
			}finally{
				if(con != null)
					con.close();
			}
			
			
		}
		return prenotabile;

	}
	
	//fai meglio
	public synchronized Collection<Prenotabile> doRetriveRicercaDisponibilita(String dataInizio,String orarioInizio,String dataFine,String orarioFine) throws SQLException{
		
		Connection con=null;
	    PreparedStatement pst=null;
	    String queryOrarioPrenotabie = "select * from fasciaOraria where orario_inizio<=? AND orario_fine>=? ";
	    
	    String query = "select * from prenotabile (data_prenotabile>=data_inizio AND dataPrenotabile<=data_fine) AND (orario_prenotabie>= orarioInizio AND orario_prenotabile<=orarioFine)";
		
		return prenotabilelist;
	}
	
	public Prenotabile doRetrieveDataPrenotabileAndFasciaOraria(String dataPrenotabile, int fasciaOrariaNumero) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		Prenotabile prenotabile=new Prenotabile();
		try {
			con=ds.getConnection();
			query = "select * from prenotabile where data_prenotabile = ? AND fascia_oraria_numero = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, dataPrenotabile);
			pst.setInt(2, fasciaOrariaNumero);
			rs = pst.executeQuery();

			if(rs.next()) {
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("caspula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));

			}

		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE , e.getMessage());
		} finally {
			try {
				if(pst != null)
					pst.close();
			}finally{
				if(con != null)
					con.close();
			}
			
			
		}
		return prenotabile;

	}
	
	//aggiungere una nuova data con i corrispondenti orari prenotabili
	
	public void doSave(Prenotabile prenotabile) throws SQLException {
		
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query="insert into e_prenotabile (data_prenotabile, capsula_id, fascia_oraria_numero) values(?,?,?)";
			con.setAutoCommit(true);
			pst = con.prepareStatement(query);
			pst.setString(1, prenotabile.getDataPrenotabile());
			pst.setInt(2, prenotabile.getCapsulaId());
			pst.setInt(3, prenotabile.getFasciaOrariaNumero());
			
			pst.executeUpdate();
		}finally {
			try {
				if(pst != null)
					pst.close();
			}finally{
				if(con != null)
					con.close();
			}
		}
	}
	
public void doDelete(String data, int id, int fasciaOraria) throws SQLException {
		
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query="delete from e_prenotabile where data_prenotabile = ? and capsula_id = ? and fascia_oraria_numero = ?";
			con.setAutoCommit(true);
			pst = con.prepareStatement(query);
			pst.setString(1, data);
			pst.setInt(2, id);
			pst.setInt(3, fasciaOraria);
			
			pst.executeUpdate();
		}finally {
			try {
				if(pst != null)
					pst.close();
			}finally{
				if(con != null)
					con.close();
			}
		}
	}
public Prenotabile doRetrieveLastDateById(int id) throws SQLException {
	ResultSet rs;
	String query;
	PreparedStatement pst=null;
	Connection con=null;
	Prenotabile prenotabile=new Prenotabile();
	try {
		con=ds.getConnection();
		query = "select * from e_prenotabile where capsula_id = ? order by data_prenotabile desc limit 1";
		pst = con.prepareStatement(query);
		pst.setInt(1, id);
		rs = pst.executeQuery();

		if(rs.next()) {
			
			prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
			prenotabile.setCapsulaId(rs.getInt("capsula_id"));
			prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));

		}

	}catch(Exception e) {
		logger.log(Level.SEVERE, e.getMessage());
		logger.log(Level.SEVERE , e.getMessage());
	} finally {
		try {
			if(pst != null)
				pst.close();
		}finally{
			if(con != null)
				con.close();
		}
		
		
	}
	return prenotabile;

}

	public Collection<Integer> doRetrieveByDataInizioDataFine(String dataInizio,String dataFine) throws SQLException{
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		Collection<Integer> idList = new ArrayList<>() ;
		try {
			con=ds.getConnection();
			query = "SELECT capsula_id FROM e_prenotabile WHERE data_prenotabile = ? AND capsula_id IN (SELECT capsula_id FROM e_prenotabile WHERE data_prenotabile = ?) GROUP BY capsula_id";
			pst = con.prepareStatement(query);
			pst.setString(1, dataInizio);
			pst.setString(2, dataFine);
			rs = pst.executeQuery();

			while(rs.next()) {
				idList.add(rs.getInt("capsula_id"));
				
			}
			

		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE , e.getMessage());
		} finally {
			try {
				if(pst != null)
					pst.close();
			}finally{
				if(con != null)
					con.close();
			}
			
		}
				
		return idList;
		
		
	}
	
	
}	
