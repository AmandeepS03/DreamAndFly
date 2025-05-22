package integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import com.mysql.cj.jdbc.MysqlDataSource;

import storage.Prenotazione;
import storage.PrenotazioneDao;

import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrenotazioneDaoIntegrationTest {

    private static DataSource ds;
    private PrenotazioneDao dao;
    private static int generatedId;
    private static ConnectionTest connessionetest = new ConnectionTest();


    @BeforeAll
    public static void initDataSource() throws SQLException {
  
    	connessionetest.connessione();
    	ds = connessionetest.getDataSource();


        // Pulisce solo i record creati dai test precedenti (opzionale)
        try (Connection con = ds.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DELETE FROM prenotazione WHERE user_account_email = 'test.utente@dummy.com'");
        }
    }

    @BeforeEach
    public void setUp() {
        dao = new PrenotazioneDao(ds);
    }

    @Test
    @Order(1)
    public void testDoSave() throws SQLException {
        Prenotazione p = new Prenotazione();
        p.setOrarioInizio("16:00");
        p.setOrarioFine("17:00");
        p.setDataInizio("2025-06-15");
        p.setDataFine("2025-06-15");
        p.setPrezzoTotale(55.0f);
        p.setDataEffettuazione("2025-05-21");
        p.setValidita(true);
        p.setRimborso(0.0f);
        p.setUserAccountEmail("mario.rossi@gmail.com");  // deve esistere
        p.setCapsulaId(1);                               // deve esistere

        // Salva e ottiene l'ID generato
        generatedId = dao.doSave(p);
        assertTrue(generatedId > 0, "L'ID generato deve essere > 0");

        // Verifica che sia recuperabile
        Prenotazione saved = dao.doRetrieveByKey(generatedId);
        assertNotNull(saved);
        assertEquals("16:00", saved.getOrarioInizio());
        assertEquals(55.0f, saved.getPrezzoTotale());
    }
    
    @Test
    @Order(2)
    public void testDoRetrieveByKey_Existing() throws SQLException {
        // Il dump contiene codice_di_accesso = 1
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertNotNull(p, "Prenotazione con ID="+generatedId+" deve esistere");
        assertEquals(generatedId, p.getCodiceDiAccesso());
    }

    

    @Test
    @Order(3)
    public void testDoUpdateValidita() throws SQLException {
        // Disattiva la prenotazione appena creata
        dao.doUpdateValidita(generatedId, false);
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertFalse(p.isValidita(), "La validita deve essere false dopo l'update");
    }

    @Test
    @Order(4)
    public void testDoUpdateRimborso() throws SQLException {
        // Imposta un rimborso
        dao.doUpdateRimborso(generatedId, 12.5f);
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertEquals(12.5f, p.getRimborso(), 0.001, "Rimborso aggiornato correttamente");
    }

    

    @Test
    @Order(5)
    public void testDoRetrievePrenotazioniByAccount() throws SQLException {
        Collection<Prenotazione> list = dao.doRetrievePrenotazioniByAccount("mario.rossi@gmail.com");
        assertNotNull(list);
        assertFalse(list.isEmpty(), "Deve tornare almeno le prenotazioni di Mario Rossi");
        // Controlla che tra queste ci sia quella appena creata
        assertTrue(list.stream().anyMatch(pr -> pr.getCodiceDiAccesso() == generatedId));
    }
    
    @Test
    @Order(6)
    public void testDoRetrievePrenotazioniByAll() throws SQLException {
        // Parametri coerenti con quelli della prenotazione creata nel testDoSave
        Integer capsulaID = 1;
        String email = "mario.rossi@gmail.com";
        String dataInizio = "2025-06-01";
        String dataFine = "2025-06-30";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByAll(capsulaID, email, dataInizio, dataFine);

        assertNotNull(results, "La lista non deve essere null");
        assertFalse(results.isEmpty(), "Deve esserci almeno una prenotazione trovata");

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found, "La prenotazione creata deve essere presente nei risultati");
    }
    
    @Test
    @Order(7)
    public void testDoRetrievePrenotazioniByAll_NoResults() throws SQLException {
        // Parametri che non dovrebbero mai trovare corrispondenze
        Integer capsulaID = 9999; // ID inesistente
        String email = "utente.inesistente@dreamfly.com"; // email non registrata
        String dataInizio = "2030-01-01"; // ben oltre l'intervallo delle date salvate
        String dataFine = "2030-12-31";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByAll(capsulaID, email, dataInizio, dataFine);

        assertNotNull(results, "La lista deve essere non null");
        assertTrue(results.isEmpty(), "Non deve esserci alcuna prenotazione trovata");
    }

    
    @Test
    @Order(8)
    public void testDoRetrievePrenotazioniByDataFine() throws SQLException {
        // Scegliamo una dataFine che includa la prenotazione salvata (15 giugno 2025)
        String limiteDataFine = "2025-06-30";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataFine(limiteDataFine);

        assertNotNull(results, "La lista non deve essere null");
        assertFalse(results.isEmpty(), "Ci si aspetta almeno una prenotazione");

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found, "La prenotazione con data_fine <= 2025-06-30 deve essere presente");
    }
    
    @Test
    @Order(9)
    public void testDoRetrievePrenotazioniByDataFine_NoResults() throws SQLException {
        String limiteDataFine = "2020-01-01"; // molto prima della data della prenotazione

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataFine(limiteDataFine);

        assertNotNull(results, "La lista deve essere non null");
        assertTrue(results.isEmpty(), "Nessuna prenotazione deve essere trovata con data_fine <= 2020-01-01");
    }
    
    @Test
    @Order(10)
    public void testDoRetrievePrenotazioniByDataInizio() throws SQLException {
        // La prenotazione creata ha data_inizio = "2025-06-15"
        String dataInizio = "2025-06-01";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataInizio(dataInizio);

        assertNotNull(results, "La lista non deve essere null");
        assertFalse(results.isEmpty(), "Almeno una prenotazione dovrebbe essere presente");

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found, "La prenotazione con data_inizio >= 2025-06-01 deve essere trovata");
    }

    @Test
    @Order(11)
    public void testDoRetrievePrenotazioniByDataInizio_NoResults() throws SQLException {
        // Nessuna prenotazione dovrebbe avere data_inizio dopo il 1° gennaio 2030
        String dataInizio = "2030-01-01";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataInizio(dataInizio);

        assertNotNull(results, "La lista deve essere non null");
        assertTrue(results.isEmpty(), "Non ci dovrebbero essere prenotazioni dopo il 2030");
    }




}
