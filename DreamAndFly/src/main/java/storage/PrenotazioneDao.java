package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class PrenotazioneDao {
private DataSource ds=null;
	
	private static final Logger logger = Logger.getLogger(CapsulaDao.class.getName());

	public PrenotazioneDao(DataSource ds) {
		super();
		this.ds=ds;
	}
	
	public Prenotazione doRetrieveByKey(Integer codiceDiAccesso) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst=null;
		Connection con=null;
		Prenotazione prenotazione=new Prenotazione();
		try {
			con=ds.getConnection();
			query = "select * from prenotazione where codice_di_acceso = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, codiceDiAccesso);
			rs = pst.executeQuery();

			if(rs.next()) {
				
				prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
				prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
				prenotazione.setOrarioFine(rs.getString("orario_fine"));
				prenotazione.setDataInizio(rs.getString("data_inizio"));
				prenotazione.setDataFine(rs.getString("data_fine"));
				prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
				prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
				prenotazione.setValidita(rs.getBoolean("validita"));
				prenotazione.setRimborso(rs.getFloat("rimborso"));
				prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
				prenotazione.setCapsulaId(rs.getInt("capsula_id"));

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
		return prenotazione;

	}
	
}
