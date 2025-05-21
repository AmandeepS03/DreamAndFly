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

import storage.AccountUser;
import storage.AccountUserDao;

public class AccountUserDao {
private DataSource ds=null;
	
	private static final Logger logger = Logger.getLogger(AccountUserDao.class.getName());

	public AccountUserDao(DataSource ds) {
		super();
		this.ds=ds;
	}



	public AccountUser doRetrieveByKey(String email) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		AccountUser accountuser=new AccountUser();
		try {
			con=ds.getConnection();
			query = "select * from user_account where email = ? ";
			pst = con.prepareStatement(query);
			pst.setString(1, email);
			rs = pst.executeQuery();

			if(rs.next()) {
				
				accountuser.setEmail(rs.getString("email"));
				accountuser.setPassword(rs.getString("passw"));
				accountuser.setName(rs.getString("nome"));
				accountuser.setSurname(rs.getString("cognome"));
				accountuser.setNumber(rs.getString("telefono"));
				accountuser.setRuolo(rs.getInt("ruolo"));

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
		return accountuser;

	}
	
	public synchronized void doUpdateNumber(String email, String cellulare) throws SQLException {
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query = "update user_account set telefono = ? where email = ? ";
			pst = con.prepareStatement(query);
			pst.setString(1, cellulare);
			pst.setString(2, email);
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
	public synchronized void doUpdatePassword(String email, String password) throws SQLException {
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query = "update user_account set passw = ? where email = ? ";
			pst = con.prepareStatement(query);
			pst.setString(1, password);
			pst.setString(2, email);
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
public void doSave(AccountUser user) throws SQLException {
		
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		try {
			con=ds.getConnection();
			query="insert into user_account(email,passw,nome,cognome,telefono) values(?,?,?,?,?)";
			con.setAutoCommit(true);
			pst = con.prepareStatement(query);
			pst.setString(1, user.getEmail());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getName());
			pst.setString(4, user.getSurname());
			pst.setString(5,user.getNumber());
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

	public synchronized Collection<AccountUser> doRetriveAll() throws SQLException {
	    Connection con=null;
	    PreparedStatement pst=null;
	    Collection<AccountUser> accountlist = new LinkedList<>();
	    
	    String query = "select * from user_account";
	    
	    try {
	      con = ds.getConnection();
	      pst=con.prepareStatement(query);
	      ResultSet rs=pst.executeQuery();
	      
	      while(rs.next()) {
	        AccountUser user=new AccountUser();
	        user.setEmail(rs.getString("email"));
	       user.setName(rs.getString("nome"));
	       user.setSurname(rs.getString("cognome"));
	        user.setNumber(rs.getString("telefono"));
	        accountlist.add(user);
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
	    
	    return accountlist;
	  }
public void doDelete(String email) throws SQLException {
	
	String query;
	PreparedStatement pst=null;
	Connection con=null;
	try {
		con=ds.getConnection();
		query="delete from user_account where email=?;";
		con.setAutoCommit(true);
		pst = con.prepareStatement(query);
		pst.setString(1, email);
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

public void doSaveGestore(AccountUser user) throws SQLException {
	
	String query;
	PreparedStatement pst=null;
	Connection con=null;
	try {
		con=ds.getConnection();
		query="insert into user_account(email,passw,nome,cognome,telefono,ruolo) values(?,?,?,?,?,?)";
		con.setAutoCommit(true);
		pst = con.prepareStatement(query);
		pst.setString(1, user.getEmail());
		pst.setString(2, user.getPassword());
		pst.setString(3, user.getName());
		pst.setString(4, user.getSurname());
		pst.setString(5,user.getNumber());
		pst.setInt(6, user.getRuolo());
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

}
