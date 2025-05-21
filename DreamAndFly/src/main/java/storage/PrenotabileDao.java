package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class PrenotabileDao {
	

	private DataSource ds=null;
	
	private static final Logger logger = Logger.getLogger(PrenotabileDao.class.getName());

	public PrenotabileDao(DataSource ds) {
		super();
		this.ds=ds;
	}
	
	public Collection<Prenotabile> doRetrieveAll() throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		Collection<Prenotabile> prenotabilelist = new LinkedList<>();

		String query = "select * from e_prenotabile";

		try {
			con = ds.getConnection();
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Prenotabile prenotabile = new Prenotabile();
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabilelist.add(prenotabile);
			}
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
		}

		return prenotabilelist;
		
	}

	
	//aggiunge una nuova data con i corrispondenti orari prenotabili	
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
	
	public void doDelete(String data, int capsula_id, int fasciaOraria) throws SQLException {
			
			String query;
			PreparedStatement pst=null;
			Connection con=null;
			try {
				con=ds.getConnection();
				query="delete from e_prenotabile where data_prenotabile = ? and capsula_id = ? and fascia_oraria_numero = ?";
				con.setAutoCommit(true);
				pst = con.prepareStatement(query);
				pst.setString(1, data);
				pst.setInt(2, capsula_id);
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
	
//ritorna l'ultima data disponibile
	public synchronized Prenotabile doRetrieveLastDateById(int id) throws SQLException {
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

	//TODO cambia nome in doRetrieveByDataInizioAndDataFine
	public synchronized Collection<Integer> doRetrieveByDataInizioDataFine(String dataInizio,String dataFine) throws SQLException{
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
	
	public synchronized boolean doRetrieveByIdAndDate(Integer capsula_id, String data) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query = "select * from e_prenotabile where data_prenotabile=? and capsula_id = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data);
			pst.setInt(2, capsula_id);
			rs = pst.executeQuery();

			if(rs.next()) {
				return true;
				
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
		return false;
	}
	
	public synchronized boolean doRetrieveByIdAndFasciaOrariaAndDate(Integer capsula_id,Integer fascia_oraria, String data) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query = "select * from e_prenotabile where capsula_id = ? and fascia_oraria_numero = ? and data_prenotabile=?";
			pst = con.prepareStatement(query);
			pst.setInt(1, capsula_id);
			pst.setInt(2, fascia_oraria);
			pst.setString(3, data);
			rs = pst.executeQuery();

			if(rs.next()) {
				return true;
				
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
		return false;
	}
	
	
	public synchronized Collection<Prenotabile> doRetrieveById(int id) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "select * from e_prenotabile where capsula_id = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while(rs.next()) {
				
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;

	}
	
	public synchronized Collection<Prenotabile> doRetrieveByDataInizio(String data) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "SELECT * FROM e_prenotabile WHERE data_prenotabile >= ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data);
			rs = pst.executeQuery();

			while(rs.next()) {
				
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;

	}

	//TODO retrieve, capsulaId
	public Collection<Prenotabile> doRetrievePrenotabiliByCapsulaAndDataInizio(Integer capsula_id, String dataInizio) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "SELECT * FROM e_prenotabile WHERE capsula_id= ? AND data_prenotabile >= ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, capsula_id);
			pst.setString(2, dataInizio);
			rs = pst.executeQuery();

			while(rs.next()) {
			
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;
	}
	
	//TODO retrieve, capsulaId nel nome
	//visualizza le capsule prenotabili fino a quella data
	public Collection<Prenotabile> doRetrievePrenotabiliByCapsulaAndDataFine(Integer capsula_id, String dataFine) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "SELECT * FROM e_prenotabile WHERE capsula_id= ? AND data_prenotabile <= ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, capsula_id);
			pst.setString(2, dataFine);
			rs = pst.executeQuery();

			while(rs.next()) {
			
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;
	}
	

	
	public synchronized Collection<Prenotabile> doRetrieveByDataFine(String data) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "SELECT * FROM e_prenotabile WHERE data_prenotabile <= ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data);
			rs = pst.executeQuery();

			while(rs.next()) {
				
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;

	}
	
	public synchronized Collection<Prenotabile> doRetrieveByDataInizioAndDataFine(String dataInizio, String dataFine) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "SELECT * FROM e_prenotabile WHERE data_prenotabile >= ? AND data_prenotabile <= ?";
			pst = con.prepareStatement(query);
			pst.setString(1, dataInizio);
			pst.setString(2, dataFine);
			rs = pst.executeQuery();

			while(rs.next()) {
				
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;

	}

	//TODO retrieve, CapsulaId nel nome
	public Collection<Prenotabile> doRetrievePrenotabileByCapsulaAndDataInizioAndDataFine(Integer capsula_id, String dataInizio,String dataFine) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		
		Collection<Prenotabile> prenotabileList = new ArrayList<>();
		try {
			con=ds.getConnection();
			query = "SELECT * FROM e_prenotabile WHERE capsula_id=? and data_prenotabile >= ? AND data_prenotabile <= ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, capsula_id);
			pst.setString(2, dataInizio);
			pst.setString(3, dataFine);
			rs = pst.executeQuery();

			while(rs.next()) {
				
				Prenotabile prenotabile=new Prenotabile();
				
				prenotabile.setDataPrenotabile(rs.getString("data_prenotabile"));
				prenotabile.setCapsulaId(rs.getInt("capsula_id"));
				prenotabile.setFasciaOrariaNumero(rs.getInt("fascia_oraria_numero"));
				
				prenotabileList.add(prenotabile);

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
		return prenotabileList;
	}
	
	
}	
