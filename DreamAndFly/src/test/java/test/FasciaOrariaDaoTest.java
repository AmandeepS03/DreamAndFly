package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import storage.Capsula;
import storage.FasciaOraria;
import storage.FasciaOrariaDao;

public class FasciaOrariaDaoTest {

	
	private DataSource ds;
	private Connection connection;
	private FasciaOrariaDao fasciaOrariaDao;
	
	@BeforeEach
    public void setUp() throws Exception {
  
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
                .thenReturn(connection = mock(Connection.class));
        fasciaOrariaDao = new FasciaOrariaDao(ds);
    }
	
	@Test
	@DisplayName("doRetrieveByKeyTest fascia oraria trovata")
	public void doRetrieveByKeyTest() throws SQLException {
		
		
		
		//Mock del preparedStatement
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		
				
		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

		
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
				
		Mockito.when(resultSet.next()).thenReturn(true); //Ci sono risultati
		Mockito.when(resultSet.getInt("numero")).thenReturn(1);
		Mockito.when(resultSet.getString("orario_inizio")).thenReturn("9:00");
		Mockito.when(resultSet.getString("orario_fine")).thenReturn("10:00");
				
				
		//Chiama il metodo da testare
		FasciaOraria fo = fasciaOrariaDao.doRetrieveByKey(1);
				
				
		assertEquals(1, fo.getNumero());
		assertEquals("9:00", fo.getorarioInizio() );
		assertEquals("10:00", fo.getorarioFine() ); 
		
		
		Mockito.verify(preparedStatement, times(1)).setInt(1, 1);
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(1)).next();
        Mockito.verify(resultSet, times(1)).getInt("numero");
        Mockito.verify(resultSet, times(1)).getString("orario_inizio");
        Mockito.verify(resultSet, times(1)).getString("orario_fine");
				
	}	
	
	//#NULL
	@Test
	@DisplayName("doRetrieveByKeyTest fascia oraria non trovata")
	public void doRetrieveByKeyFallitoTest() throws SQLException {
		
		
		
		//Mock del preparedStatement
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		
				
		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

		
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
				
		Mockito.when(resultSet.next()).thenReturn(false); //Ci sono risultati
		
				
				
		//Chiama il metodo da testare
		FasciaOraria fo = fasciaOrariaDao.doRetrieveByKey(1);
				
		
		assertNull(fo);
		
        Mockito.verify(preparedStatement, times(1)).setInt(1, 1);
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(1)).next();  // L'utente non è stato trovato, quindi next() dovrebbe essere chiamato solo una volta
        

		resultSet.close();
				
				
		}
	
	@Test
	@DisplayName("doRetriveAll funzionante")
	public void doRetriveAllTest() throws SQLException{
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
	    ResultSet resultSet = Mockito.mock(ResultSet.class);
	    

	    
	    Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

	    Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

	    Mockito.when(resultSet.next()).thenReturn(true, true, false);
	    Mockito.when(resultSet.getInt("numero")).thenReturn(1, 2);
	    Mockito.when(resultSet.getString("orario_inizio")).thenReturn("8:00","9:00");
	    Mockito.when(resultSet.getString("orario_fine")).thenReturn("9:00", "10:00");

	    Collection<FasciaOraria> result = fasciaOrariaDao.doRetriveAll();

	    assertEquals(2, result.size());

	    
	    Mockito.verify(preparedStatement, times(1)).close();
	    
	    Mockito.verify(resultSet, times(3)).next();
        
        Mockito.verify(resultSet, times(2)).getInt("numero");
        
        Mockito.verify(resultSet, times(2)).getString("orario_inizio");
        Mockito.verify(resultSet, times(2)).getString("orario_fine");
	}
	
	@Test
	@DisplayName("doRetrieveKeyByOrarioInizioTest")
	public void doRetrieveKeyByOrarioInizioTest() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Configura il mock per ritornare il preparedStatement quando il metodo prepareStatement viene chiamato sulla connessione
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Configura il mock per ritornare un risultato simulato quando executeQuery() è chiamato
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        // Configura il mock per ritornare valori simulati quando chiamati i metodi del ResultSet
        Mockito.when(resultSet.next()).thenReturn(true); // Ci sono risultati
        Mockito.when(resultSet.getInt("numero")).thenReturn(3);
        Mockito.when(resultSet.getString("orario_inizio")).thenReturn("9:00");
        Mockito.when(resultSet.getString("orario_fine")).thenReturn("10:00");

        int result = fasciaOrariaDao.doRetrieveByOrarioInizio("9:00");

       
        // Verifica che il risultato sia quello atteso
        assertEquals(3, result);
        

        // Verifica che i metodi del preparedStatement siano stati chiamati correttamente
        Mockito.verify(preparedStatement, times(1)).setString(1, "9:00");
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(1)).next();
        Mockito.verify(resultSet, times(1)).getInt("numero");
        
        
        
        resultSet.close();

	}
	
	/*
	//serve?
	@Test
	@DisplayName("doRetrieveByOrarioInizio fallito")
	public void doRetrieveByOrarioInizioTestFallito() throws SQLException{
		
	}
	*/
	
	@Test
	@DisplayName("doRetrieveByOrarioFineTest")
	public void doRetrieveByOrarioFineTest() throws SQLException{
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Configura il mock per ritornare il preparedStatement quando il metodo prepareStatement viene chiamato sulla connessione
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Configura il mock per ritornare un risultato simulato quando executeQuery() è chiamato
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        // Configura il mock per ritornare valori simulati quando chiamati i metodi del ResultSet
        Mockito.when(resultSet.next()).thenReturn(true); // Ci sono risultati
        Mockito.when(resultSet.getInt("numero")).thenReturn(3);
        Mockito.when(resultSet.getString("orario_inizio")).thenReturn("9:00");
        Mockito.when(resultSet.getString("orario_fine")).thenReturn("10:00");

        int result = fasciaOrariaDao.doRetrieveByOrarioFine("10:00");

       
        // Verifica che il risultato sia quello atteso
        assertEquals(3, result);
        

        // Verifica che i metodi del preparedStatement siano stati chiamati correttamente
        Mockito.verify(preparedStatement, times(1)).setString(1, "10:00");
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(1)).next();
        Mockito.verify(resultSet, times(1)).getInt("numero");
        
        
        
        resultSet.close();
	}
	
}
