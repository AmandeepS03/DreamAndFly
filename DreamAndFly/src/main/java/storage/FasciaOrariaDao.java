package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class FasciaOrariaDao {
	private DataSource ds=null;
	private Connection connection=null;
	private static final Logger logger = Logger.getLogger(CapsulaDao.class.getName());

	public FasciaOrariaDao(DataSource ds) {
		super();
		this.ds=ds;
		
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//#NULL
	public FasciaOraria doRetrieveByKey(int numero) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		FasciaOraria fasciaOraria=null; //questo dara problemi nel moento in cui 
		//si chiama questo metodo e nella servlet non è gestito il caso in cui 
		//il valore restituito, cioe la capsula è null
		//allora tu nella servlet vai a mettere un if(faciaOraria==null) allinizio di tutto 
		//e gestisci il reindirizzamento e gli errori
		//VEDI LoginServlet e cerca il commento --> #NULL, per la soluzione
	
		try {
			con=ds.getConnection();
			query = "select * from fascia_oraria where numero = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, numero);
			rs = pst.executeQuery();

			if(rs.next()) {
				fasciaOraria = new FasciaOraria();
				fasciaOraria.setNumero(rs.getInt("numero"));
				fasciaOraria.setorarioInizio(rs.getString("orario_inizio"));
				fasciaOraria.setorarioFine(rs.getString("orario_fine"));

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
		return fasciaOraria;

	}
	
	public synchronized Collection<FasciaOraria> doRetriveAll() throws SQLException {
	    Connection con=null;
	    PreparedStatement pst=null;
	    Collection<FasciaOraria> fasciaOrarialist = new LinkedList<>();
	    
	    String query = "select * from fascia_oraria";
	    
	    try {
	      con = ds.getConnection();
	      pst=con.prepareStatement(query);
	      ResultSet rs=pst.executeQuery();
	      
	      while(rs.next()) {
	        FasciaOraria fasciaOraria=new FasciaOraria();
	        fasciaOraria.setNumero(rs.getInt("numero"));
			fasciaOraria.setorarioInizio(rs.getString("orario_inizio"));
			fasciaOraria.setorarioFine(rs.getString("orario_fine"));
	        
			fasciaOrarialist.add(fasciaOraria);
	        }
	    }finally {
	      try {
	        if(pst != null)
	          pst.close();
	      }finally{
	        if(con != null)
	          con.close();
	      }
	  }
	    
	    return fasciaOrarialist;
	  }
	
	public int doRetrieveByOrarioInizio(String orario) throws SQLException {
			int numero=0;
		 	Connection con=null;
		    PreparedStatement pst=null;
		    ResultSet rs=null;
		    String query = "select numero from fascia_oraria where orario_inizio = ?";
		    
		    try {
		      con = ds.getConnection();
		      pst = con.prepareStatement(query);
			  pst.setString(1, orario);
			  rs = pst.executeQuery();

				if(rs.next()) {
					numero = rs.getInt("numero");

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
			return numero;

		}
	
	
	
	public int doRetrieveByOrarioFine(String orario) throws SQLException {
		int numero=0;
	 	Connection con=null;
	    PreparedStatement pst=null;
	    ResultSet rs;
	    String query = "select numero from fascia_oraria where orario_fine = ?";
	    
	    try {
	      con = ds.getConnection();
	      pst = con.prepareStatement(query);
		  pst.setString(1, orario);
		  rs = pst.executeQuery();

			if(rs.next()) {
				numero = rs.getInt("numero");

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
		return numero;

	}
}
