package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
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
		
		boolean verifiedPassword = false;
		
		if("password".equals(user.getPassword())) {
			verifiedPassword = true;
		}
		
		assertEquals(true, verifiedPassword);
		
		assertEquals("peppeverdi@gmail.com", user.getEmail());
		assertEquals("Giuseppe", user.getName());
		assertEquals("Verdi", user.getSurname());
		assertEquals("1234578962", user.getNumber());
		assertEquals(0, user.getRuolo());
		
		
		
	}
	
	//utente non trovato
	@Test
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
	
	
	
	
	
}
