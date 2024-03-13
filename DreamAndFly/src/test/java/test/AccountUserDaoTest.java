package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mysql.cj.protocol.Resultset;

import storage.AccountUser;
import storage.AccountUserDao;

public class AccountUserDaoTest {

	private DataSource ds;
	private Connection connection;
	private AccountUserDao userDao;
	
	@BeforeEach
    public void setUp() throws Exception {
  
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
                .thenReturn(connection = mock(Connection.class));
        userDao = new AccountUserDao(ds);
    }
	
	//utente trovato
	@Test
	@DisplayName("E1_P1 email corretta, password corretta")
	public void doRetrieveByKeyTest() throws Exception{
		//Mock del preparedStatement
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		
		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
		
		Mockito.when(resultSet.next()).thenReturn(true); //Ci sono risultati
		Mockito.when(resultSet.getString("email")).thenReturn("peppeverdi@gmail.com");
		Mockito.when(resultSet.getString("passw")).thenReturn("password");
		Mockito.when(resultSet.getString("nome")).thenReturn("Giuseppe");
		Mockito.when(resultSet.getString("cognome")).thenReturn("Verdi");
		Mockito.when(resultSet.getString("telefono")).thenReturn("1234578962");
		Mockito.when(resultSet.getInt("ruolo")).thenReturn(0);
		
		//Chiama il metodo da testare
		AccountUser user = userDao.doRetrieveByKey("peppeverdi@gmail.com");
		
		
		
		assertEquals("password", user.getPassword());
		
		assertEquals("peppeverdi@gmail.com", user.getEmail());
		assertEquals("Giuseppe", user.getName());
		assertEquals("Verdi", user.getSurname());
		assertEquals("1234578962", user.getNumber());
		assertEquals(0, user.getRuolo());
		
		
		
	}
	
	//utente non trovato
	@Test
	@DisplayName("E2_P2 email errata")
	public void doRetrieveByKeyNonTrovatoTest() throws SQLException{
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		
		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
		
		Mockito.when(resultSet.next()).thenReturn(false);
		
		//Chiama il metodo da testare
		AccountUser user = userDao.doRetrieveByKey("peppeverdi@gmail.com");
		
		assertNull(user);
		
		resultSet.close();
	}
	
	//email trovata password errata
	
	@Test
	@DisplayName("E1_P2 email corretta password errata")
	public void doRetrieveByKeyNonTrovatoPasswordErrataTest() throws SQLException {
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
		
		 /* Utente da trovare, dati inseriti dall utente */
        Mockito.when(resultSet.next()).thenReturn(true); // Ci sono risultati
        Mockito.when(resultSet.getString("email")).thenReturn("peppeverdi@gmail.com");
		Mockito.when(resultSet.getString("passw")).thenReturn("password1");
		Mockito.when(resultSet.getString("nome")).thenReturn("Giuseppe");
		Mockito.when(resultSet.getString("cognome")).thenReturn("Verdi");
		Mockito.when(resultSet.getString("telefono")).thenReturn("1234578962");
		Mockito.when(resultSet.getInt("ruolo")).thenReturn(0);

        AccountUser user = userDao.doRetrieveByKey("peppeverdi@gmail.com");
        
        boolean verifiedPassword = false;
        if("password".equals(user.getPassword())) {
        	verifiedPassword = true;
        }
        
        assertEquals(false, verifiedPassword);
		
		assertEquals("peppeverdi@gmail.com", user.getEmail());
		assertEquals("Giuseppe", user.getName());
		assertEquals("Verdi", user.getSurname());
		assertEquals("1234578962", user.getNumber());
		assertEquals(0, user.getRuolo());
        
	}
	
	
	@Test
	@DisplayName("E1_P1_N1_C1_CEL1 regisreazione effettuata")
	public void doSaveTest() throws SQLException {
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        
        AccountUser user = new AccountUser("mario","rossi","mariorossi@gmail.com","Capsula2024","3495422687",0);

        assertTrue(userDao.doSave(user));
        // Verifica che il metodo setString sia stato chiamato con i valori corretti
        Mockito.verify(preparedStatement, times(1)).setString(1, user.getEmail());
        Mockito.verify(preparedStatement, times(1)).setString(2, user.getPassword());
        Mockito.verify(preparedStatement, times(1)).setString(3, user.getName());
        Mockito.verify(preparedStatement, times(1)).setString(4, user.getSurname());
        Mockito.verify(preparedStatement, times(1)).setString(5, user.getNumber());
        

        // Verifica che il metodo executeUpdate sia stato chiamato
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
        
        
	}
	
	/*
	@Test
	@DisplayName("E1_P1_N1_C1_CEL1 registrazione fallita senza dati")
	public void doSaveTest_EmptyFields() throws SQLException {
	    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	    Mockito.when(preparedStatement.executeUpdate()).thenReturn(0); // Simula l'inserimento fallito

	    // Crea un oggetto AccountUser con tutti i campi vuoti o nulli
	    AccountUser user = new AccountUser("", "", "", "", "", 0);

	    assertFalse(userDao.doSave(user));

	    // Verifica che il metodo executeUpdate sia stato chiamato
	    Mockito.verify(preparedStatement, times(1)).executeUpdate();
	}
	*/
	
	@Test
	@DisplayName("C1 UpdateNumber")
	public void doUpdateNumberTest() throws SQLException {
	    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

	    
	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	    Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
	    
	    AccountUser user = new AccountUser("mario", "rossi", "mariorossi@gmail.com", "Capsula2024", "3495422687", 0);

	    assertTrue(userDao.doUpdateNumber(user.getEmail(), "1257845967"));
	    
	    // Verifica che i parametri siano impostati correttamente
	    Mockito.verify(preparedStatement, times(1)).setString(1, "1257845967");
	    Mockito.verify(preparedStatement, times(1)).setString(2, "mariorossi@gmail.com");
	    
	    // Verifica che il metodo executeUpdate sia stato chiamato
	    Mockito.verify(preparedStatement, times(1)).executeUpdate();
	    
	    // Verifica che il metodo close sia stato chiamato sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).close();
	}

	
	@Test
	@DisplayName("C1 UpdateNumber - Aggiornamento fallito")
	public void doUpdateNumberTest_Failure() throws SQLException {
	    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	    Mockito.when(preparedStatement.executeUpdate()).thenReturn(0); // Simula l'aggiornamento fallito

	    AccountUser user = new AccountUser("mario", "rossi", "mariorossi@gmail.com", "Capsula2024", "3495422687", 0);

	    assertFalse(userDao.doUpdateNumber(user.getEmail(), "1257845967"));
	    
	    // Verifica che i parametri siano impostati correttamente
	    Mockito.verify(preparedStatement, times(1)).setString(1, "1257845967");
	    Mockito.verify(preparedStatement, times(1)).setString(2, "mariorossi@gmail.com");
	    
	    // Verifica che il metodo executeUpdate sia stato chiamato
	    Mockito.verify(preparedStatement, times(1)).executeUpdate();
	    
	    // Verifica che il metodo close sia stato chiamato sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).close();
	}
	
	@Test
	@DisplayName("P1 Test di aggiornamento della password")
	
	public void doUpdatePasswordTest() throws SQLException {
	    // Crea un mock di PreparedStatement
	    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

	    // Configura il comportamento del mock della connessione e del PreparedStatement
	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	    Mockito.when(preparedStatement.executeUpdate()).thenReturn(1); // Simula l'aggiornamento riuscito

	    // Crea un oggetto AccountUser per il test
	    AccountUser user = new AccountUser("mario", "rossi", "mariorossi@gmail.com", "Capsula2024", "3495422687", 0);

	    // Chiama il metodo doUpdateNumber() che si sta testando e verifica che restituisca true (aggiornamento riuscito)
	    assertTrue(userDao.doUpdateNumber(user.getEmail(), "1257845967"));
	    
	    // Verifica che i parametri siano impostati correttamente sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).setString(1, "1257845967");
	    Mockito.verify(preparedStatement, times(1)).setString(2, "mariorossi@gmail.com");
	    
	    // Verifica che il metodo executeUpdate sia stato chiamato una volta sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).executeUpdate();
	    
	    // Verifica che il metodo close sia stato chiamato sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).close();
	}

	@Test
	@DisplayName("P2 UpdatePassword - Aggiornamento fallito")
	public void doUpdatePasswordTest_Failure() throws SQLException {
	    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	    Mockito.when(preparedStatement.executeUpdate()).thenReturn(0); // Simula l'aggiornamento fallito

	    
	    
	    AccountUser user = new AccountUser("mario", "rossi", "mariorossi@gmail.com", "NuovaPassword123", "1257845967", 0);
	    
	    

	    assertFalse(userDao.doUpdatePassword(user.getEmail(), user.getPassword()) );
	    
	    // Verifica che i parametri siano impostati correttamente
	    Mockito.verify(preparedStatement, times(1)).setString(1, user.getPassword());
	    Mockito.verify(preparedStatement, times(1)).setString(2, user.getEmail());
	    
	    // Verifica che il metodo executeUpdate sia stato chiamato
	    Mockito.verify(preparedStatement, times(1)).executeUpdate();

	    // Verifica che il metodo close sia stato chiamato sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).close();
	}
	
	@Test
	@DisplayName("TCU1_5_1 doRetrieveAllTest")
	public void doRetrieveAllUsersTest() throws Exception {
	    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
	    ResultSet resultSet = Mockito.mock(ResultSet.class);
	    

	    
	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

	    Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

	    Mockito.when(resultSet.next()).thenReturn(true, true, false);
	    Mockito.when(resultSet.getString("email")).thenReturn("mariorossi@gmail.com", "rossimario@gmail.com");
	    Mockito.when(resultSet.getString("nome")).thenReturn("Mario", "Rossi");
	    Mockito.when(resultSet.getString("cognome")).thenReturn("Rossi", "Mario");
	    Mockito.when(resultSet.getString("telefono")).thenReturn("3495422687", "3495422688");

	    Collection<AccountUser> result = userDao.doRetriveAll();

	    assertEquals(2, result.size());

	    
	    Mockito.verify(preparedStatement, times(1)).close();
	    
	    Mockito.verify(resultSet, times(3)).next();
        
        Mockito.verify(resultSet, times(2)).getString("email");
        
        Mockito.verify(resultSet, times(2)).getString("nome");
        Mockito.verify(resultSet, times(2)).getString("cognome");
        Mockito.verify(resultSet, times(2)).getString("telefono");

	}
	
	
	



}
