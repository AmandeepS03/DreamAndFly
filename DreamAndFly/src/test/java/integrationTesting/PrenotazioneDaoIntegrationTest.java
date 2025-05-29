package integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import storage.Prenotazione;
import storage.PrenotazioneDao;

import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrenotazioneDaoIntegrationTest {

    private static DataSource ds;
    private PrenotazioneDao dao;
    private static Integer generatedId;
    private static ConnectionTest connessionetest = new ConnectionTest();


    @BeforeAll
    public static void initDataSource() throws SQLException {
  
    	connessionetest.connessione();
    	ds = connessionetest.getDataSource();


        
    }

    @BeforeEach
    public void setUp() {
        dao = new PrenotazioneDao(ds);
    }
    
    
    // TC9_2_1_IT : doSave prenotazione non presente nel DB, prenotazione inserita
    @Test
    @Order(1)
    public void TC9_2_1_IT() throws SQLException {
        Prenotazione p = new Prenotazione();
        p.setOrarioInizio("16:00");
        p.setOrarioFine("17:00");
        p.setDataInizio("2025-06-15");
        p.setDataFine("2025-06-15");
        p.setPrezzoTotale(55.0f);
        p.setDataEffettuazione("2025-05-21");
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
    
    
  //TC9_1_1_IT doRetrieveByKey:codice non presente, ritorna un oggetto vuoto.
    @Test
    @Order(2)
    public void TC9_1_1_IT() throws SQLException {
        
    	int nonExistingId = 999999;

        Prenotazione p = dao.doRetrieveByKey(nonExistingId);

        // Verifica che l'oggetto non sia null
        assertNotNull(p);
     // Verifica che i campi siano vuoti o di default
        assertNull(p.getUserAccountEmail());
        assertEquals(0, p.getCapsulaId());
        assertNull(p.getDataInizio());
        assertNull(p.getDataFine());
        assertEquals(0.0f, p.getPrezzoTotale());
        assertFalse(p.isValidita());
    	
    	
       
    }
    
    //TC9_1_2_IT doRetrieveByKey: codice presente nel db. Ritorna la prenotazione relativa a quel codiceDiAccesso.
    @Test
    @Order(3)
    public void TC9_1_2_IT() throws SQLException {
    	Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertNotNull(p, "Prenotazione con ID="+generatedId+" deve esistere");
        assertEquals(generatedId, p.getCodiceDiAccesso());	
        
    }
    
    
    
    //TC9_3_2_IT doUpdateValidita: codice presente, modifica della validita effettuata
    @Test
    @Order(4)
    public void TC9_3_2_IT() throws SQLException {
        // Disattiva la prenotazione appena creata
        dao.doUpdateValidita(generatedId, false);
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertFalse(p.isValidita());
    }
    
    //TC9_3_1_IT doUpdateValidita: codice non presente del db, la validita non viene aggiornata
    @Test
    @Order(5)
    public void TC9_3_1_IT() throws SQLException {
        int nonExistingId = 999999;

        // Prova a disattivare una prenotazione inesistente
        dao.doUpdateValidita(nonExistingId, false);

        Prenotazione p = dao.doRetrieveByKey(nonExistingId);

        // Verifica che l'oggetto non sia null
        assertNotNull(p);

        // Verifica che i campi siano valori di default perché la prenotazione non esiste
        assertNull(p.getUserAccountEmail());
        assertEquals(0, p.getCapsulaId());
        assertNull(p.getDataInizio());
        assertNull(p.getDataFine());

        // La validità potrebbe essere false, perché l'update è stato chiamato,
        // ma siccome la prenotazione non esiste, non ci aspettiamo che cambi realmente.
        // Puoi anche rimuovere questo controllo oppure controllare che sia false per sicurezza
        assertFalse(p.isValidita());
    }

    
    
    //TC9_4_1_IT doUpdateRimborso:codice non presente nel db. Modifica del rimborso non effettuato.    
    @Test
    @Order(6)
    public void TC9_4_1_IT() throws SQLException {
        int nonExistingId = 999999;

        // Prova a impostare rimborso su una prenotazione inesistente
        dao.doUpdateRimborso(nonExistingId, 15.0f);

        Prenotazione p = dao.doRetrieveByKey(nonExistingId);

        // Verifica che l'oggetto non sia null
        assertNotNull(p, "L'oggetto non deve essere null");

        // Controlla che i campi siano valori di default perché la prenotazione non esiste
        assertNull(p.getUserAccountEmail());
        assertEquals(0, p.getCapsulaId());
        assertNull(p.getDataInizio());
        assertNull(p.getDataFine());

        // Rimborso dovrebbe essere il valore di default (0.0f) perché la prenotazione non esiste realmente
        assertEquals(0.0f, p.getRimborso());
    }
    
  //TC9_4_2_IT doUpdateRimborso: codice presente nel db. Modifica del rimborso effettuato.
    @Test
    @Order(7)
    public void TC9_4_2_IT() throws SQLException {
        // Imposta un rimborso
        dao.doUpdateRimborso(generatedId, 12.5f);
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertEquals(12.5f, p.getRimborso());
    }


    
    //TC9_7_2_IT DoRetrievePrenotazioniByAccount: email presente. Ritorna la lista contenente le prenotazioni effettuate da quell’indirizzo e-mail
    @Test
    @Order(8)
    public void TC9_7_2_IT() throws SQLException {
        Collection<Prenotazione> list = dao.doRetrievePrenotazioniByAccount("mario.rossi@gmail.com");
        assertNotNull(list);
        assertFalse(list.isEmpty());
        // Controlla che tra queste ci sia quella appena creata
        assertTrue(list.stream().anyMatch(pr -> pr.getCodiceDiAccesso() == generatedId));
    }
    
    //TC9_7_1_IT DoRetrievePrenotazioniByAccount: email non presente nel db. Ritorna un oggetto vuoto.
    @Test
    @Order(9)
    public void TC9_7_1_IT() throws SQLException {
        String emailInesistente = "utente.inesistente@dummy.com";

        Collection<Prenotazione> list = dao.doRetrievePrenotazioniByAccount(emailInesistente);

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    //TC9_14_5_IT DoRetrievePrenotazioniByAll: prenotazione presente
    @Test
    @Order(10)
    public void TC9_14_5_IT() throws SQLException {
        // Parametri coerenti con quelli della prenotazione creata nel testDoSave
        Integer capsulaID = 1;
        String email = "mario.rossi@gmail.com";
        String dataInizio = "2025-06-01";
        String dataFine = "2025-06-30";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByAll(capsulaID, email, dataInizio, dataFine);

        assertNotNull(results);
        assertFalse(results.isEmpty());

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found);
    }
    
    //TC9_14_1_TC9_14_2_TC9_14_3_TC9_14_4_IT - doRetrievePrenotazioniByAll: dati non presenti
    @Test
    @Order(11)
    public void TC9_14_1_TC9_14_2_TC9_14_3_TC9_14_4_IT() throws SQLException {
        // Parametri che non dovrebbero mai trovare corrispondenze
        Integer capsulaID = 9999; // ID inesistente
        String email = "utente.inesistente@dreamfly.com"; // email non registrata
        String dataInizio = "2030-01-01"; // ben oltre l'intervallo delle date salvate
        String dataFine = "2030-12-31";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByAll(capsulaID, email, dataInizio, dataFine);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    //TC9_9_1_IT DoRetrievePrenotazioniByDataFine: Esiste almeno una data <= DataFine nel DB.
    @Test
    @Order(12)
    public void TC9_9_1_IT() throws SQLException {
        // Scegliamo una dataFine che includa la prenotazione salvata (15 giugno 2025)
        String limiteDataFine = "2025-06-30";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataFine(limiteDataFine);

        assertNotNull(results);
        assertFalse(results.isEmpty());

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found);
    }
    
    //TC9_9_2_IT DoRetrievePrenotazioniByDataFine: Nessuna data <= di quella fornita è presente DB.
    @Test
    @Order(13)
    public void TC9_9_2_IT() throws SQLException {
        String limiteDataFine = "2020-01-01"; // molto prima della data della prenotazione

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataFine(limiteDataFine);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
    
    //TC9_8_2_IT DoRetrievePrenotazioniByDataInizio: Esiste almeno una data >= DataInizio nel DB. 
    @Test
    @Order(14)
    public void TC9_8_2_IT() throws SQLException {
        // La prenotazione creata ha data_inizio = "2025-06-15"
        String dataInizio = "2025-06-01";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataInizio(dataInizio);

        assertNotNull(results);
        assertFalse(results.isEmpty());

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found);
    }
    
    //TC9_8_1_IT DoRetrievePrenotazioniByDataInizio: Nessuna data >= di quella fornita è presente DB.
    @Test
    @Order(15)
    public void TC9_8_1_IT() throws SQLException {
        // Nessuna prenotazione dovrebbe avere data_inizio dopo il 1° gennaio 2030
        String dataInizio = "2030-01-01";

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByDataInizio(dataInizio);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
    
    //TC9_6_2_IT DoRetrievePrenotazioniByNumeroCapsulaAll: numeroCapsula presente nel DB.
    @Test
    @Order(16)
    public void TC9_6_2_IT() throws SQLException {
        // La prenotazione salvata in testDoSave usa capsula_id = 1
        int capsulaId = 1;

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByNumeroCapsulaAll(capsulaId);

        assertNotNull(results);
        assertFalse(results.isEmpty());

        boolean found = results.stream()
            .anyMatch(p -> p.getCodiceDiAccesso() == generatedId);

        assertTrue(found);
    }
    
    //TC9_6_1_IT DoRetrievePrenotazioniByNumeroCapsulaAll: numeroCapsula non presente nel DB.
    @Test
    @Order(17)
    public void TC9_6_1_IT() throws SQLException {
        int capsulaId = 9999; // Capsula che sicuramente non esiste

        Collection<Prenotazione> results = dao.doRetrievePrenotazioniByNumeroCapsulaAll(capsulaId);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }


    @AfterAll
    public static void tearDown() throws SQLException {
    	
        // Elimina la prenotazione creata per i test, se esiste
        if (generatedId != null) {
            try (Connection con = ds.getConnection()){
                PreparedStatement pst = con.prepareStatement("DELETE FROM prenotazione WHERE codice_di_accesso = ?"); {
                pst.setInt(1, generatedId);
                pst.executeUpdate();
            }
        }
    }

    


}}
