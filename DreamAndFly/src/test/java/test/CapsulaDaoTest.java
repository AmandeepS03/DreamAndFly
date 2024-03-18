package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import storage.AccountUser;
import storage.Capsula;
import storage.CapsulaDao;

public class CapsulaDaoTest {
	
	private DataSource ds;
	private Connection connection;
	private CapsulaDao capsulaDao;
	
	@BeforeEach
    public void setUp() throws Exception {
  
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
                .thenReturn(connection = mock(Connection.class));
        capsulaDao = new CapsulaDao(ds);
    }
	
	
	@Test
	@DisplayName("doRetrieveByKeyTest capsula trovata")
	public void doRetrieveByKeyTest() throws SQLException {
		
		
		
		//Mock del preparedStatement
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		
				
		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

		
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
				
		Mockito.when(resultSet.next()).thenReturn(true); //Ci sono risultati
		Mockito.when(resultSet.getInt("id")).thenReturn(1);
		Mockito.when(resultSet.getFloat("prezzo_orario")).thenReturn((float) 9);
		Mockito.when(resultSet.getString("tipologia")).thenReturn("deluxe");
				
				
		//Chiama il metodo da testare
		Capsula capsula = capsulaDao.doRetrieveByKey(1);
				
				
		assertEquals(1, capsula.getId());
		assertEquals((float) 9, capsula.getPrezzo_orario());
		assertEquals("deluxe", capsula.getTipologia() ); 
				
				
		}	
	

@Test
@DisplayName("doRetrieveByKeyTest capsula non trovata")
public void doRetrieveByKeyFallitoTest() throws SQLException {
	
	
	
	//Mock del preparedStatement
	PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
	ResultSet resultSet = Mockito.mock(ResultSet.class);
	
			
	Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

	
	Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
			
	Mockito.when(resultSet.next()).thenReturn(false); //Ci sono risultati
	
			
			
	//Chiama il metodo da testare
	Capsula capsula = capsulaDao.doRetrieveByKey(1);
			
			
	assertNull(capsula);
	
	resultSet.close();
			
			
	}	

	@Test
	@DisplayName("doRetrieveByKeyTest updatePrezzo fatto")
	public void doUpdatePrezzoOrarioTest() throws SQLException{
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

	    
	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	    Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
	    
	    Capsula capsula = new Capsula(1, 7.0f, "deluxe");

	    assertTrue(capsulaDao.doUpdatePrezzoOrario(capsula.getId(), capsula.getPrezzo_orario()));
	    
	    // Verifica che i parametri siano impostati correttamente
	    Mockito.verify(preparedStatement, times(1)).setFloat(1, 7.0f);
	    Mockito.verify(preparedStatement, times(1)).setInt(2, 1);
	    
	    
	    // Verifica che il metodo executeUpdate sia stato chiamato
	    Mockito.verify(preparedStatement, times(1)).executeUpdate();
	    
	    // Verifica che il metodo close sia stato chiamato sul PreparedStatement
	    Mockito.verify(preparedStatement, times(1)).close();
	}
}

