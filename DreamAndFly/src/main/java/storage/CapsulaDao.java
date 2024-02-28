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

public class CapsulaDao {
private DataSource ds=null;
	
	private static final Logger logger = Logger.getLogger(CapsulaDao.class.getName());

	public CapsulaDao(DataSource ds) {
		super();
		this.ds=ds;
	}
	
	public Capsula doRetrieveByKey(Integer id) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		Capsula capsula=new Capsula();
		try {
			con=ds.getConnection();
			query = "select * from capsula where id = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			if(rs.next()) {
				
				capsula.setId(rs.getInt("id"));
				capsula.setPrezzo_orario(rs.getFloat("prezzo_orario"));
				capsula.setTipologia(rs.getString("tipologia"));

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
		return capsula;

	}
	
	public synchronized void doUpdatePrezzoOrario(Integer id, float prezzo_orario) throws SQLException {
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query = "update capsula set prezzo_orario = ? where id = ? ";
			pst = con.prepareStatement(query);
			pst.setFloat(1, prezzo_orario);
			pst.setInt(2, id);
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
	
	public void doSave(Capsula user) throws SQLException {
			
			String query;
			PreparedStatement pst=null;
			Connection con=null;
			try {
				con=ds.getConnection();
				query="insert into capsula(id, prezzo_orario, tipologia) values(?,?,?)";
				con.setAutoCommit(true);
				pst = con.prepareStatement(query);
				pst.setInt(1, user.getId());
				pst.setFloat(2, user.getPrezzo_orario());
				pst.setString(3, user.getTipologia());
				
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
	
	public synchronized Collection<Capsula> doRetriveAll() throws SQLException {
	    Connection con=null;
	    PreparedStatement pst=null;
	    Collection<Capsula> capsulalist = new LinkedList<>();
	    
	    String query = "select * from capsula";
	    
	    try {
	      con = ds.getConnection();
	      pst=con.prepareStatement(query);
	      ResultSet rs=pst.executeQuery();
	      
	      while(rs.next()) {
	        Capsula capsula=new Capsula();
	        capsula.setId(rs.getInt("id"));
	       capsula.setPrezzo_orario(rs.getFloat("prezzo_orario"));
	       capsula.setTipologia(rs.getString("tipologia"));
	        
	        capsulalist.add(capsula);
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
	    
	    return capsulalist;
	  }
}
