package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import utils.PrenotazioneWrapper;

public class PrenotazioneDao {
	private DataSource ds = null;

	private static final Logger logger = Logger.getLogger(PrenotazioneDao.class.getName());

	public PrenotazioneDao(DataSource ds) {
		super();
		this.ds = ds;
	}

	public Prenotazione doRetrieveByKey(Integer codiceDiAccesso) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		Prenotazione prenotazione = new Prenotazione();
		try {
			con = ds.getConnection();
			query = "select * from prenotazione where codice_di_acceso = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, codiceDiAccesso);
			rs = pst.executeQuery();

			if (rs.next()) {

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

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}

		}
		return prenotazione;

	}

	public synchronized int doSave(Prenotazione prenotazione) throws SQLException {
		int codice_di_accesso = 0;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null; // ResultSet per ottenere i valori generati automaticamente
		try {
			con = ds.getConnection();
			query = "insert into prenotazione(orario_inizio, orario_fine, data_inizio, data_fine, prezzo_totale, data_effettuazione, user_account_email, capsula_id) values(?,?,?,?,?,?,?,?)";
			con.setAutoCommit(true);
			pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, prenotazione.getOrarioInizio());
			pst.setString(2, prenotazione.getOrarioFine());
			pst.setString(3, prenotazione.getDataInizio());
			pst.setString(4, prenotazione.getDataFine());
			pst.setFloat(5, prenotazione.getPrezzoTotale());
			pst.setString(6, prenotazione.getDataEffettuazione());
			pst.setString(7, prenotazione.getUserAccountEmail());
			pst.setInt(8, prenotazione.getCapsulaId());
			pst.executeUpdate();
			// Ottieni i valori generati automaticamente
	        rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            codice_di_accesso = rs.getInt(1); // Ottieni il valore del codice di accesso
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
		return codice_di_accesso;
	}


	public synchronized void doUpdateValidita(Integer codiceDiAccesso, boolean validita) throws SQLException {
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		try {
			con = ds.getConnection();
			query = "update prenotazione set validita = ? where codice_di_accesso = ? ";
			pst = con.prepareStatement(query);
			pst.setBoolean(1, validita);
			pst.setInt(2, codiceDiAccesso);
			pst.executeUpdate();
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
		}
	}

	public  synchronized void doUpdateRimborso(Integer codiceDiAccesso, float rimborso) throws SQLException {
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		try {
			con = ds.getConnection();
			/*
			 * implementa lo lagica di rimborso oppure calcoli rimborso prima di chiamare la
			 * funzione di updateRimboso
			 */
			query = "update prenotazione set rimborso = ? where codice_di_accesso = ? ";
			pst = con.prepareStatement(query);
			pst.setFloat(1, rimborso);
			pst.setInt(2, codiceDiAccesso);
			pst.executeUpdate();
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
		}
	}

	public synchronized Collection<Prenotazione> doRetrieveByDataInizio(String dataInizio) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		Collection<Prenotazione> prenotazionelist = new LinkedList<>();
		Prenotazione prenotazione = new Prenotazione();
		try {
			con = ds.getConnection();
			query = "select * from prenotazione where dataInizio >= ? order by data_inizio ASC"; /**/
			pst = con.prepareStatement(query);
			pst.setString(1, dataInizio);
			rs = pst.executeQuery();

			while (rs.next()) {

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
				prenotazionelist.add(prenotazione);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}

		}
		return prenotazionelist;

	}

	public  synchronized Collection<Prenotazione> doRetrieveByDataInizioAndDataFine(String dataInizio, String dataFine) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		Collection<Prenotazione> prenotazionelist = new LinkedList<>();
		Prenotazione prenotazione = new Prenotazione();
		try {
			con = ds.getConnection();
			query = "select * from prenotazione where data_inizio >= ? AND data_fine <= ? order by data_fine ASC";
			pst = con.prepareStatement(query);
			pst.setString(1, dataInizio);
			pst.setString(2, dataFine);
			rs = pst.executeQuery();
			
			if (rs.next()) {

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
				prenotazionelist.add(prenotazione);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}

		}
		return prenotazionelist;

	}

	public synchronized Prenotazione doRetrieveByDataFine(String dataFine) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		Prenotazione prenotazione = new Prenotazione();
		Collection<Prenotazione> prenotazionelist = new LinkedList<>();
		try {
			con = ds.getConnection();
			query = "select * from prenotazione where data_fine <= ? order by data_fine DESC"; /*mostra le prenotazioni dalla data di fine fino alla prima prenotazione*/
			pst = con.prepareStatement(query);
			pst.setString(1, dataFine);
			rs = pst.executeQuery();
			
			if (rs.next()) {

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
				prenotazionelist.add(prenotazione);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}

		}
		return prenotazione;

	}

	

	public synchronized Prenotazione doRetrieveByNumeroCapsula(Integer numeroCapsula) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		Prenotazione prenotazione = new Prenotazione();
		try {
			con = ds.getConnection();
			query = "select * from prenotazione where numeroCapsula = ? "; /**/
			pst = con.prepareStatement(query);
			pst.setInt(1, numeroCapsula);
			rs = pst.executeQuery();

			if (rs.next()) {

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

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}

		}
		return prenotazione;

	}
	
	public synchronized Prenotazione deletePrenotazione(Integer codicediAccesso) throws SQLException {
		ResultSet rs;
		String query;
		PreparedStatement pst = null;
		Connection con = null;
		Prenotazione prenotazione = new Prenotazione();
		try {
			con = ds.getConnection();
			query = "delete from prenotazione where codice_di_accesso = ? "; /**/
			pst = con.prepareStatement(query);
			pst.setInt(1, codicediAccesso);
			int numeroRighe = pst.executeUpdate();
			
			

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}

		}
		return prenotazione;

	}
	//gestore 
		public synchronized Collection<Prenotazione> doRetriveAll() throws SQLException {
			Connection con = null;
			PreparedStatement pst = null;
			Collection<Prenotazione> prenotazionelist = new LinkedList<>();

			String query = "select * from prenotazione";

			try {
				con = ds.getConnection();
				pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					Prenotazione prenotazione = new Prenotazione();
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

					prenotazionelist.add(prenotazione);
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

			return prenotazionelist;
		}
		
			
	

	  public synchronized Collection<PrenotazioneWrapper> doRetriveByEmail(String email,int chiamante) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<PrenotazioneWrapper> prenotazioneWrapperlist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT p.*, c.tipologia FROM prenotazione AS p JOIN capsula AS c ON p.capsula_id = c.id where p.user_account_email=?;"; 
	  pst =con.prepareStatement(query);
	  pst.setString(1, email);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione
		    PrenotazioneWrapper prenotazioneWrapper = new PrenotazioneWrapper(); // Creare un nuovo oggetto PrenotazioneWrapper ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    if (chiamante == 1) { // 1=gestore
		        prenotazione.setValidita(rs.getBoolean("validita"));
		        prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		    }

		    // Impostare i valori del wrapper
		    prenotazioneWrapper.setTipologiaCapsula(rs.getString("tipologia"));
		    prenotazioneWrapper.setPrenotazione(prenotazione);

		    // Aggiungere il wrapper alla lista
		    prenotazioneWrapperlist.add(prenotazioneWrapper);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  return prenotazioneWrapperlist;
	  
	  }

		
	  public synchronized Collection<Prenotazione> doRetriveEmailWithPrenotazione() throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT user_account_email FROM prenotazione group by user_account_email "; 
	  pst =con.prepareStatement(query);
	  
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		    
		    prenotazionelist.add(prenotazione);

		    
		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  return prenotazionelist;
	  
	  }
	  
	  
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByNumeroCapsulaAll(Integer numeroCapsula) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where capsula_id=?"; 
	  pst =con.prepareStatement(query);
	  pst.setInt(1, numeroCapsula);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  return prenotazionelist;
	  
	  }
	  
	  
	  
	  
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByAccount(String email) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where user_account_email=?"; 
	  pst =con.prepareStatement(query);
	  pst.setString(1, email);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  return prenotazionelist;
	  
	  }
	  
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByDataInizio(String dataInizio) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where data_inizio>=?"; 
	  pst =con.prepareStatement(query);
	  pst.setString(1, dataInizio);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  System.out.println("Prenotazioni: ");
	  for(Prenotazione p: prenotazionelist) {
		  System.out.println(p.getDataInizio());
	  }
	  
	  return prenotazionelist;
	  
	  }
	  
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByDataFine(String dataFine) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where data_fine<=? order by data_fine DESC"; 
	  pst =con.prepareStatement(query);
	  pst.setString(1, dataFine);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  
	  return prenotazionelist;
	  
	  }
	  
	  
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByDataInizioAndFine(String dataInizio, String dataFine) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where data_inizio>= ? AND data_fine<=?"; 
	  pst =con.prepareStatement(query);
	  pst.setString(1, dataInizio);
	  pst.setString(2, dataFine);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
		  	  
	  
	  return prenotazionelist;
	  
	  }
	  
	   
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByDataInizioAndAccount(String dataInizio, String userAccountEmail) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where data_inizio>= ? AND user_account_email=?"; 
	  pst =con.prepareStatement(query);
	  pst.setString(1, dataInizio);
	  pst.setString(2, userAccountEmail);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  
	  return prenotazionelist;
	  
	  }
	
	  public synchronized Collection<Prenotazione> doRetrivePrenotazioniByNumeroCapsulaAndAccount(Integer capsulaID, String userAccountEmail) throws SQLException { 
		  ResultSet rs; 
		  String query; 
		  PreparedStatement pst=null;
	  Connection con=null;
	  Collection<Prenotazione> prenotazionelist = new LinkedList<>();
	  try {
	  con=ds.getConnection(); 
	  query = "SELECT * FROM prenotazione where capsula_id= ? AND user_account_email=?"; 
	  pst =con.prepareStatement(query);
	  pst.setInt(1, capsulaID);
	  pst.setString(2, userAccountEmail);
	  rs = pst.executeQuery();
	  
	  while (rs.next()) {
		    Prenotazione prenotazione = new Prenotazione(); // Creare un nuovo oggetto Prenotazione ad ogni iterazione

		    // Impostare i valori della prenotazione
		    prenotazione.setCodiceDiAccesso(rs.getInt("codice_di_accesso"));
		    prenotazione.setOrarioInizio(rs.getString("orario_inizio"));
		    prenotazione.setOrarioFine(rs.getString("orario_fine"));
		    prenotazione.setDataInizio(rs.getString("data_inizio"));
		    prenotazione.setDataFine(rs.getString("data_fine"));
		    prenotazione.setPrezzoTotale(rs.getFloat("prezzo_totale"));
		    prenotazione.setDataEffettuazione(rs.getString("data_effettuazione"));
		    prenotazione.setRimborso(rs.getFloat("rimborso"));
		    prenotazione.setCapsulaId(rs.getInt("capsula_id"));

		    
		    prenotazione.setValidita(rs.getBoolean("validita"));
		    prenotazione.setUserAccountEmail(rs.getString("user_account_email"));
		
		    prenotazionelist.add(prenotazione);

		}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (pst != null)
					pst.close();
			} finally {
				if (con != null)
					con.close();
			}
	  
	  
	  } 
	  
	  
	  return prenotazionelist;
	  
	  }

	  
	

}
