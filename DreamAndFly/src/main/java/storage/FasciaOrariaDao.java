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
	
	private static final Logger logger = Logger.getLogger(CapsulaDao.class.getName());

	public FasciaOrariaDao(DataSource ds) {
		super();
		this.ds=ds;
	}
	
	public FasciaOraria doRetrieveByKey(int numero) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		FasciaOraria fasciaOraria=new FasciaOraria();
		try {
			con=ds.getConnection();
			query = "select * from fascia_oraria where numero = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, numero);
			rs = pst.executeQuery();

			if(rs.next()) {
				
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
}
