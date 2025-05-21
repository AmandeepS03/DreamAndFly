package unitTesting;

import org.junit.jupiter.api.*;
import storage.Prenotazione;
import storage.PrenotazioneDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrenotazioneDaoTest {

    private DataSource mockDataSource;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private PrenotazioneDao dao;

    @BeforeEach
    void setUp() throws Exception {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new PrenotazioneDao(mockDataSource);
    }

    // TC9_1_1 - doRetrieveByKey: codice non presente nel Db
    @Test
    void TC9_1_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Prenotazione result = dao.doRetrieveByKey(9999);
        assertNotNull(result);
        assertNull(result.getCodiceDiAccesso());
    }

    // TC9_1_2 - doRetrieveByKey: codice presente nel DB
    @Test
    void TC9_1_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("codice_di_accesso")).thenReturn(1234);
        Prenotazione result = dao.doRetrieveByKey(1234);
        assertEquals(1234, result.getCodiceDiAccesso());
    }
    
    
    
 // TP8_2_1 doSave: Prenotazione non presente nel DB
    @Test
    void TC9_2_1() throws Exception {
        Prenotazione p = new Prenotazione();
        p.setOrarioInizio("10:00");
        p.setOrarioFine("11:00");
        p.setDataInizio("2025-06-01");
        p.setDataFine("2025-06-01");
        p.setPrezzoTotale(100.0f);
        p.setDataEffettuazione("2025-05-20");
        p.setUserAccountEmail("utente@example.com");
        p.setCapsulaId(1);

        // Prepara i mock
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
            .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1234); // codice di accesso simulato

        assertDoesNotThrow(() -> dao.doSave(p));
    }

    
    // TC9_2_2 - doSave: Prenotazione già presente nel DB
    @Test
    void TC9_2_2() throws Exception {
        Prenotazione p = new Prenotazione();
        p.setOrarioInizio("10:00");
        p.setOrarioFine("11:00");
        p.setDataInizio("2025-06-01");
        p.setDataFine("2025-06-01");
        p.setPrezzoTotale(100.0f);
        p.setDataEffettuazione("2025-05-20");
        p.setUserAccountEmail("utente@example.com");
        p.setCapsulaId(1);

        // Setup mock della connessione e prepared statement
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
            .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate())
            .thenThrow(new SQLIntegrityConstraintViolationException());

        assertThrows(SQLIntegrityConstraintViolationException.class, () -> dao.doSave(p));
    }

 // TC9_3_1 doUpdateValidita: codice non presente nel DB
    @Test
    void TC9_3_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doUpdateValidita(9999, true);
        verify(mockPreparedStatement).setBoolean(1, true);
        verify(mockPreparedStatement).setInt(2, 9999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC9_3_2 doUpdateValidita: codice presente nel DB
    @Test
    void TC9_3_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doUpdateValidita(1234, false);
        verify(mockPreparedStatement).setBoolean(1, false);
        verify(mockPreparedStatement).setInt(2, 1234);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC9_4_1 doUpdateRimborso: codice non presente nel DB
    @Test
    void TC9_4_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doUpdateRimborso(9999, 5.0f);
        verify(mockPreparedStatement).setFloat(1, 5.0f);
        verify(mockPreparedStatement).setInt(2, 9999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC9_4_2 doUpdateRimborso: codice presente nel DB
    @Test
    void TC9_4_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doUpdateRimborso(1234, 15.0f);
        verify(mockPreparedStatement).setFloat(1, 15.0f);
        verify(mockPreparedStatement).setInt(2, 1234);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC9_5_1 - doRetrieveByEmail: Email non presente nel DB
    @Test
    void TC9_5_1_doRetrieveByEmail_EmailNonPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByEmail("missing@example.com", 0).isEmpty());
    }

    // TC9_5_2 - doRetrieveByEmail: Email presente nel DB
    @Test
    void TC9_5_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrieveByEmail("user@example.com", 0).size());
    }

    // TC9_6_1 - doRetrievePrenotazioniByNumeroCapsulaAll: numeroCapsula non presente nel DB
    @Test
    void TC9_6_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByNumeroCapsulaAll(999).isEmpty());
    }

    // TC9_6_2 - doRetrievePrenotazioniByNumeroCapsulaAll: numeroCapsula presente nel DB
    @Test
    void TC9_6_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByNumeroCapsulaAll(1).size());
    }
    
    
    // TC9_7_1 -doRetrievePrenotazioniByAccount: Email non presente nel DB
    @Test
    void TC9_7_1() throws Exception {
        String emailInesistente = "nonpresente@example.com";

        // Mock delle dipendenze
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simula che il ResultSet è vuoto
        when(mockResultSet.next()).thenReturn(false);

        Collection<Prenotazione> result = dao.doRetrievePrenotazioniByAccount(emailInesistente);

        assertNotNull(result); // La lista non deve essere null
        assertTrue(result.isEmpty()); // La lista deve essere vuota
    }


    // TC9_7_2 - doRetrievePrenotazioniByAccount: Email presente nel DB
    @Test
    void TC9_7_2() throws Exception {
    	when(mockResultSet.next()).thenReturn(true, false); // una sola riga nel resultset
        assertNotNull(dao.doRetrievePrenotazioniByAccount("user@example.com"));
    }



    // TC9_8_1 - doRetrievePrenotazioniByDataInizio: Nessuna data >= dataInizio presente nel DB
    @Test
    void TC9_8_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByDataInizio("2099-01-01").isEmpty());
    }

    // TC9_8_2 - doRetrievePrenotazioniByDataInizio: Almeno una data >= dataInizio è presente nel DB
    @Test
    void TC9_8_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByDataInizio("2025-06-01").size());
    }

    // TC9_9_1 - doRetrievePrenotazioniByDataFine: Nessuna data <= dataFine è presente nel DB
    @Test
    void TC9_9_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByDataFine("2000-01-01").isEmpty());
    }

    // TC9_9_2 - doRetrievePrenotazioniByDataFine: Almeno una data <= dataFine è presente nel DB
    @Test
    void TC9_9_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByDataFine("2025-06-10").size());
    }

    // TC9_10_1 - doRetrievePrenotazioniByDataInizioAndFine: Nessuna data >= dataInizio è presente nel DB
    @Test
    void TC9_10_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByDataInizioAndFine("2099-01-01", "2099-12-31").isEmpty());
    }

    // TC9_10_2 - doRetrievePrenotazioniByDataInizioAndFine:  Nessuna data <= dataFine è presente nel DB
    @Test
    void TC9_10_2 () throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByDataInizioAndFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC9_10_3 - doRetrievePrenotazioniByDataInizioAndFine: Esiste almeno una  data che rientra nell intervallo
    @Test
    void TC9_10_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByDataInizioAndFine("2025-06-01", "2025-06-10").size());
    }

    // TC9_11_1 - Nessuna data >= di data inizio 
    @Test
    void TC9_11_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByDataInizioAndAccount("2025-06-01", "user@example.com").isEmpty());
    }

    // TC9_11_2 - doRetrievePrenotazioniByDataInizioAndAccount: Email non presente
    @Test
    void TC9_11_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByDataInizioAndAccount("2025-06-01", "missing@example.com").isEmpty());
    }

    // TC9_11_3 - doRetrievePrenotazioniByDataInizioAndAccount: Tutti i dati presenti
    @Test
    void TC9_11_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByDataInizioAndAccount("2025-06-01", "user@example.com").size());
    }

    // TC9_12_1 - doRetrieveByDataFineAndAccount: Nessuna data <= dataFine è presente nel DB
    @Test
    void TC9_12_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataFineAndAccount("2000-01-01", "user@example.com").isEmpty());
    }

    // TC9_12_2 - doRetrieveByDataFineAndAccount: Email non presente
    @Test
    void TC9_12_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataFineAndAccount("2025-06-01", "missing@example.com").isEmpty());
    }

    // TC9_12_3 - doRetrieveByDataFineAndAccount: Tutti i dati presenti
    @Test
    void TC9_12_3 () throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrieveByDataFineAndAccount("2025-06-10", "user@example.com").size());
    }

    


    // TC9_13_1 doRetrievePrenotazioniByNumeroCapsulaAndAccount: CapsulaID non presente nel DB
    @Test
    void TC9_13_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByNumeroCapsulaAndAccount(999, "user@example.com").isEmpty());
    }

    // TC9_13_2 doRetrievePrenotazioniByNumeroCapsulaAndAccount: UserAccountEmail non presente nel DB
    @Test
    void TC9_13_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByNumeroCapsulaAndAccount(1, "missing@example.com").isEmpty());
    }

    // TC9_13_3 doRetrievePrenotazioniByNumeroCapsulaAndAccount: Dati presenti nel DB
    @Test
    void TC9_13_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByNumeroCapsulaAndAccount(1, "user@example.com").size());
    }

    // TC9_14_1 doRetrievePrenotazioniByAll: Nessuna data >= di quella di inizio è presente DB
    @Test
    void TC9_14_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAll(1, "user@example.com", "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC9_14_2 doRetrievePrenotazioniByAll: Nessuna data <= di quella di fine è presente DB
    @Test
    void TC9_14_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAll(1, "user@example.com", "2025-01-01", "2025-01-02").isEmpty());
    }

    // TC9_14_3 doRetrievePrenotazioniByAll: capsulaID non presente DB
    @Test
    void TC9_14_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAll(999, "user@example.com", "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC9_14_4 doRetrievePrenotazioniByAll: userAccountEmail non presente nel DB
    @Test
    void TC9_14_4() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAll(1, "missing@example.com", "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC9_14_5 doRetrievePrenotazioniByAll: Dati presenti nel DB
    @Test
    void TC9_14_5() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByAll(1, "user@example.com", "2025-06-01", "2025-06-10").size());
    }

    // TC9_15_1 doRetrievePrenotazioniByAccountAndIdAndDataInizio: Nessuna data >= di quella di inizio
    @Test
    void TC9_15_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndIdAndDataInizio(999, "user@example.com", "2099-01-01").isEmpty());
    }

    // TC9_15_2 doRetrievePrenotazioniByAccountAndIdAndDataInizio: CapsulaID non presente DB
    @Test
    void TC9_15_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndIdAndDataInizio(999, "user@example.com", "2025-06-01").isEmpty());
    }

    // TC9_15_3 doRetrievePrenotazioniByAccountAndIdAndDataInizio: UserAccountEmail non presente DB
    @Test
    void TC9_15_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndIdAndDataInizio(1, "missing@example.com", "2025-06-01").isEmpty());
    }

    // TC9_15_4 doRetrievePrenotazioniByAccountAndIdAndDataInizio: Dati presenti nel DB
    @Test
    void TC9_15_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByAccountAndIdAndDataInizio(1, "user@example.com", "2025-06-01").size());
    }

    // TC9_16_1 doRetrievePrenotazioniByAccountAndIdAndDataFine: Nessuna data <= datafine è presente nel DB 
    @Test
    void TC9_16_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndIdAndDataFine(1, "user@example.com", "2000-01-01").isEmpty());
    }

    // TC9_16_2 doRetrievePrenotazioniByAccountAndIdAndDataFine: CapsulaID non presente
    @Test
    void TC9_16_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndIdAndDataFine(999, "user@example.com", "2025-06-10").isEmpty());
    }

    // TC9_16_3 doRetrievePrenotazioniByAccountAndIdAndDataFine: UserAccountEmail non presente
    @Test
    void TC9_16_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndIdAndDataFine(1, "missing@example.com", "2025-06-10").isEmpty());
    }

    // TC9_16_4 doRetrievePrenotazioniByAccountAndIdAndDataFine: Tutti i dat presenti
    @Test
    void TC9_16_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByAccountAndIdAndDataFine(1, "user@example.com", "2025-06-10").size());
    }

    // TC9_17_1 doRetrievePrenotazioneByCapsulaAndDataInizio: Nessuna data >= dataInizio è presente nel DB
    @Test
    void TC9_17_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataInizio(1, "2099-01-01").isEmpty());
    }

    // TC9_17_2 doRetrievePrenotazioneByCapsulaAndDataInizio: CapsulaID non presente
    @Test
    void TC9_17_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataInizio(999, "2025-06-01").isEmpty());
    }

    // TC9_17_3 doRetrievePrenotazioneByCapsulaAndDataInizio: Tutti i dati presenti
    @Test
    void TC9_17_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioneByCapsulaAndDataInizio(1, "2025-06-01").size());
    }

    // TC9_18_1 doRetrievePrenotazioneByCapsulaAndDataFine: Nessuna data <= dataFine è presente nel db
    @Test
    void TC9_18_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataFine(1, "2000-01-01").isEmpty());
    }

    // TC9_18_2 doRetrievePrenotazioneByCapsulaAndDataFine: NumeroCapsulaSelect non presente
    @Test
    void TC9_18_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataFine(999, "2025-06-01").isEmpty());
    }

    // TC9_18_3 doRetrievePrenotazioneByCapsulaAndDataFine: Dati presenti
    @Test
    void TC9_18_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioneByCapsulaAndDataFine(1, "2025-06-10").size());
    }

    // TC9_19_1 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: Nessuna data >= datainizio è presente nel Db
    @Test
    void TC9_19_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC9_19_2 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: NumeroCapsulaSelect non presente
    @Test
    void TC9_19_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine(999, "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC9_19_3 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: Nessuna data <= fine è presente nel DB
    @Test
    void TC9_19_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2025-01-01", "2025-01-02").isEmpty());
    }

    // TC9_19_4 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: Dati presenti
    @Test
    void TC9_19_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2025-06-01", "2025-06-10").size());
    }

    // TC9_20_1 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Nessuna data >= dataInizio è presente nel DB
    @Test
    void TC9_20_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndDataInizioAndDataFine("user@example.com", "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC9_20_2 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Account non presente
    @Test
    void TC9_20_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndDataInizioAndDataFine("missing@example.com", "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC9_20_3 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Nessuna data <= datafine è presente nel DB
    @Test
    void TC9_20_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotazioniByAccountAndDataInizioAndDataFine("user@example.com", "2025-01-01", "2025-01-02").isEmpty());
    }

    // TC9_20_4 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Dati presenti
    @Test
    void TC9_20_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrievePrenotazioniByAccountAndDataInizioAndDataFine("user@example.com", "2025-06-01", "2025-06-10").size());
    }
    
    
    @AfterEach
    void tearDown() throws Exception {
        verify(mockPreparedStatement, atLeastOnce()).close();
        verify(mockConnection, atLeastOnce()).close();
    }

}
