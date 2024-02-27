package storage;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
