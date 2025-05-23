package integrationTesting;

import org.junit.jupiter.api.*;

import storage.FasciaOraria;
import storage.FasciaOrariaDao;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.sql.*;

public class FasciaOrariaDaoIntegrationTest {

	 private static DataSource dataSource;
	    private FasciaOrariaDao dao;
	    private static ConnectionTest connessionetest = new ConnectionTest();


	    @BeforeAll
	    public static void initDataSource() {
	    	connessionetest.connessione();
	    	dataSource = connessionetest.getDataSource();
	    }

    @BeforeEach
    public void setup() {
        dao = new FasciaOrariaDao(dataSource);
    }
    
    
    // TC7_1_1_IT: doRetrieveByKey numero non esistente
    @Test
    public void TC7_1_1_IT() throws SQLException {
        FasciaOraria f = dao.doRetrieveByKey(999);
        assertEquals(0, f.getNumero()); // L'oggetto viene restituito con numero 0 se non trovato
    }

    // TC7_1_2_IT: doRetrieveByKey numero esistente
    @Test
    public void TC7_1_2_IT() throws SQLException {
        FasciaOraria f = dao.doRetrieveByKey(1);
        assertEquals(1, f.getNumero());
        assertEquals("00:00", f.getorarioInizio());
        assertEquals("01:00", f.getorarioFine());
    }

 
    // TC7_2_1_IT: doRetrieveByOrarioInizio: orario di inizio non esistente
    @Test
    public void TC7_2_1_IT() throws SQLException {
        int numero = dao.doRetrieveByOrarioInizio("25:00");
        assertEquals(0, numero); // 0 se non trovato
    }
    
    // TC7_2_2_IT: doRetrieveByOrarioInizio: orario di inizio esistente
    @Test
    public void TC7_2_2_IT() throws SQLException {
        int numero = dao.doRetrieveByOrarioInizio("12:00");
        assertEquals(13, numero);
    }
    
    // TC7_3_1_IT: doRetrieveByOrarioFine: orario di fine non esistente
    @Test
    public void TC7_3_1_IT() throws SQLException {
        int numero = dao.doRetrieveByOrarioFine("99:00");
        assertEquals(0, numero); // 0 se non trovato
    }


    // TC7_3_2_IT: doRetrieveByOrarioFine: orario di fine esistente
    @Test
    public void TC7_3_2_IT() throws SQLException {
        int numero = dao.doRetrieveByOrarioFine("23:00");
        assertEquals(23, numero);
    }

  
}

