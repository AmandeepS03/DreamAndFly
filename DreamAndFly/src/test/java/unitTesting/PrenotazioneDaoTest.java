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

    // TC8_1_1 - doRetrieveByKey: codice non presente
    @Test
    void TC8_1_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Prenotazione result = dao.doRetrieveByKey(9999);
        assertNotNull(result);
        assertNull(result.getCodiceDiAccesso());
    }

    // TC8_1_2 - doRetrieveByKey: codice presente
    @Test
    void TC8_1_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("codice_di_accesso")).thenReturn(1234);
        Prenotazione result = dao.doRetrieveByKey(1234);
        assertEquals(1234, result.getCodiceDiAccesso());
    }
    
    
    
 // TP8_2_1 doSave: Prenotazione non presente nel DB
    @Test
    void TP8_2_1() throws Exception {
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

    
    // TC8_2_2 - doSave: Prenotazione già presente
    @Test
    void TC8_2_2_doSave_PrenotazioneGiaPresente() throws Exception {
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

 // TC8_3_1 doUpdateValidita: codice non presente
    @Test
    void TC8_3_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doUpdateValidita(9999, true);
        verify(mockPreparedStatement).setBoolean(1, true);
        verify(mockPreparedStatement).setInt(2, 9999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_3_2 doUpdateValidita: codice presente
    @Test
    void TC8_3_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doUpdateValidita(1234, false);
        verify(mockPreparedStatement).setBoolean(1, false);
        verify(mockPreparedStatement).setInt(2, 1234);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_4_1 doUpdateRimborso: codice non presente
    @Test
    void TC8_4_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doUpdateRimborso(9999, 5.0f);
        verify(mockPreparedStatement).setFloat(1, 5.0f);
        verify(mockPreparedStatement).setInt(2, 9999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_4_2 doUpdateRimborso: codice presente
    @Test
    void TC8_4_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doUpdateRimborso(1234, 15.0f);
        verify(mockPreparedStatement).setFloat(1, 15.0f);
        verify(mockPreparedStatement).setInt(2, 1234);
        verify(mockPreparedStatement).executeUpdate();
    }


    



    // TC8_5_1 - doRetrieveByDataInizio: data >= dataInizio presente
    @Test
    void TC8_5_1() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrieveByDataInizio("2025-06-01");
        assertNotNull(result);
    }

    // TC8_5_2 - nessuna data >=
    @Test
    void TC8_5_2_doRetrieveByDataInizio_NessunaData() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizio("2099-01-01").isEmpty());
    }

    // TC8_6_1 - Nessuna data >= dataInizio
    @Test
    void TC8_6_1_doRetrieveByDataInizioAndFine_NessunaDataMaggiore() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioAndDataFine("2099-01-01", "2099-01-10").isEmpty());
    }

    // TC8_6_2 - Nessuna data <= dataFine
    @Test
    void TC8_6_2_doRetrieveByDataInizioAndFine_NessunaDataMinore() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioAndDataFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_6_3 - Date in range
    @Test
    void TC8_6_3_doRetrieveByDataInizioAndFine_DatePresenti() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrieveByDataInizioAndDataFine("2025-06-01", "2025-06-10");
        assertNotNull(result);
    }

    // TC8_7_1 - Email non presente
    @Test
    void TC8_7_1_doRetrieveByEmail_EmailNonPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetriveByEmail("missing@example.com", 0).isEmpty());
    }

    // TC8_7_2 - Email presente
    @Test
    void TC8_7_2_doRetrieveByEmail_EmailPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetriveByEmail("user@example.com", 0).size());
    }

    // TC8_8_1 - numeroCapsula non presente
    @Test
    void TC8_8_1_doRetrievePrenotazioniByNumeroCapsulaAll_CapsulaNonPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByNumeroCapsulaAll(999).isEmpty());
    }

    // TC8_8_2 - numeroCapsula presente
    @Test
    void TC8_8_2_doRetrievePrenotazioniByNumeroCapsulaAll_CapsulaPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByNumeroCapsulaAll(1).size());
    }
    
    
    // TC8_9_1 -doRetrivePrenotazioniByAccount: Email non presente
    @Test
    void TC8_9_1() throws Exception {
        String emailInesistente = "nonpresente@example.com";

        // Mock delle dipendenze
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simula che il ResultSet è vuoto
        when(mockResultSet.next()).thenReturn(false);

        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByAccount(emailInesistente);

        assertNotNull(result); // La lista non deve essere null
        assertTrue(result.isEmpty()); // La lista deve essere vuota
    }


    // TC8_9_2 - doRetrivePrenotazioniByAccount: Email presente, modificato
    @Test
    void TC8_9_2() throws Exception {
    	when(mockResultSet.next()).thenReturn(true, false); // una sola riga nel resultset
        assertNotNull(dao.doRetrivePrenotazioniByAccount("user@example.com"));
    }



    // TC8_10_1 - doRetrivePrenotazioniByDataInizio: Nessuna data >= dataInizio
    @Test
    void TC8_10_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByDataInizio("2099-01-01").isEmpty());
    }

    // TC8_10_2 - doRetrivePrenotazioniByDataInizio: Almeno una data >= dataInizio
    @Test
    void TC8_10_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByDataInizio("2025-06-01").size());
    }

    // TC8_11_1 - doRetrivePrenotazioniByDataFine: Nessuna data <= dataFine
    @Test
    void TC8_11_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByDataFine("2000-01-01").isEmpty());
    }

    // TC8_11_2 - doRetrivePrenotazioniByDataFine: Almeno una data <= dataFine
    @Test
    void TC8_11_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByDataFine("2025-06-10").size());
    }

    // TC8_12_1 - doRetrivePrenotazioniByDataInizioAndFine: Nessuna data >= dataInizio
    @Test
    void TC8_12_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByDataInizioAndFine("2099-01-01", "2099-12-31").isEmpty());
    }

    // TC8_12_2 - doRetrivePrenotazioniByDataInizioAndFine:  Nessuna data <= dataFine
    @Test
    void TC8_12_2 () throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByDataInizioAndFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_12_3 - doRetrivePrenotazioniByDataInizioAndFine: Almeno una data valida
    @Test
    void TC8_12_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByDataInizioAndFine("2025-06-01", "2025-06-10").size());
    }

    // TC8_13_1 - Nessuna data >= di data inizio
    @Test
    void TC8_13_1_doRetrievePrenotazioniByDataInizioAndAccount_NessunaData() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByDataInizioAndAccount("2025-06-01", "user@example.com").isEmpty());
    }

    // TC8_13_2 - Email non presente
    @Test
    void TC8_13_2_doRetrievePrenotazioniByDataInizioAndAccount_EmailNonPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByDataInizioAndAccount("2025-06-01", "missing@example.com").isEmpty());
    }

    // TC8_13_3 - Tutto presente
    @Test
    void TC8_13_3_doRetrievePrenotazioniByDataInizioAndAccount_TuttiPresenti() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByDataInizioAndAccount("2025-06-01", "user@example.com").size());
    }

    // TC8_14_1 - Nessuna data <=
    @Test
    void TC8_14_1_doRetrieveByDataFineAndAccount_NessunaData() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataFineAndAccount("2000-01-01", "user@example.com").isEmpty());
    }

    // TC8_14_2 - Email non presente
    @Test
    void TC8_14_2_doRetrieveByDataFineAndAccount_EmailNonPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataFineAndAccount("2025-06-01", "missing@example.com").isEmpty());
    }

    // TC8_14_3 - Tutto presente
    @Test
    void TC8_14_3_doRetrieveByDataFineAndAccount_TuttiPresenti() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrieveByDataFineAndAccount("2025-06-10", "user@example.com").size());
    }

    


    // TC8_15_1 doRetrivePrenotazioniByNumeroCapsulaAndAccount: CapsulaID non presente DB
    @Test
    void TC8_15_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByNumeroCapsulaAndAccount(999, "user@example.com").isEmpty());
    }

    // TC8_15_2 doRetrivePrenotazioniByNumeroCapsulaAndAccount: UserAccountEmail non presente DB
    @Test
    void TC8_15_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByNumeroCapsulaAndAccount(1, "missing@example.com").isEmpty());
    }

    // TC8_15_3 doRetrivePrenotazioniByNumeroCapsulaAndAccount: Dati presenti nel DB
    @Test
    void TC8_15_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByNumeroCapsulaAndAccount(1, "user@example.com").size());
    }

    // TC8_16_1 doRetrievePrenotazioniByAll: Nessuna data >= di quella di inizio è presente DB
    @Test
    void TC8_16_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAll(1, "user@example.com", "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC8_16_2 doRetrievePrenotazioniByAll: Nessuna data <= di quella di fine è presente DB
    @Test
    void TC8_16_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAll(1, "user@example.com", "2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_16_3 doRetrievePrenotazioniByAll: capsulaID non presente DB
    @Test
    void TC8_16_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAll(999, "user@example.com", "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC8_16_4 doRetrievePrenotazioniByAll: userAccountEmail non presente nel DB
    @Test
    void TC8_16_4() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAll(1, "missing@example.com", "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC8_16_5 doRetrievePrenotazioniByAll: Dati presenti nel DB
    @Test
    void TC8_16_5() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByAll(1, "user@example.com", "2025-06-01", "2025-06-10").size());
    }

    // TC8_17_1 doRetrievePrenotazioniByAccountAndIdAndDataInizio: Nessuna data >= di quella di inizio
    @Test
    void TC8_17_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndIdAndDataInizio(999, "user@example.com", "2099-01-01").isEmpty());
    }

    // TC8_17_2 doRetrievePrenotazioniByAccountAndIdAndDataInizio: CapsulaID non presente DB
    @Test
    void TC8_17_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndIdAndDataInizio(999, "user@example.com", "2025-06-01").isEmpty());
    }

    // TC8_17_3 doRetrievePrenotazioniByAccountAndIdAndDataInizio: UserAccountEmail non presente DB
    @Test
    void TC8_17_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndIdAndDataInizio(1, "missing@example.com", "2025-06-01").isEmpty());
    }

    // TC8_17_4 doRetrievePrenotazioniByAccountAndIdAndDataInizio: Dati presenti nel DB
    @Test
    void TC8_17_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByAccountAndIdAndDataInizio(1, "user@example.com", "2025-06-01").size());
    }

    // TC8_18_1 doRetrievePrenotazioniByAccountAndIdAndDataFine: Nessuna data <= fine
    @Test
    void TC8_18_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndIdAndDataFine(1, "user@example.com", "2000-01-01").isEmpty());
    }

    // TC8_18_2 doRetrievePrenotazioniByAccountAndIdAndDataFine: CapsulaID non presente
    @Test
    void TC8_18_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndIdAndDataFine(999, "user@example.com", "2025-06-10").isEmpty());
    }

    // TC8_18_3 doRetrievePrenotazioniByAccountAndIdAndDataFine: UserAccountEmail non presente
    @Test
    void TC8_18_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndIdAndDataFine(1, "missing@example.com", "2025-06-10").isEmpty());
    }

    // TC8_18_4 doRetrievePrenotazioniByAccountAndIdAndDataFine: Tutto presente
    @Test
    void TC8_18_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByAccountAndIdAndDataFine(1, "user@example.com", "2025-06-10").size());
    }

    // TC8_19_1 doRetrievePrenotazioneByCapsulaAndDataInizio: Nessuna data >=
    @Test
    void TC8_19_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataInizio(1, "2099-01-01").isEmpty());
    }

    // TC8_19_2 doRetrievePrenotazioneByCapsulaAndDataInizio: CapsulaID non presente
    @Test
    void TC8_19_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataInizio(999, "2025-06-01").isEmpty());
    }

    // TC8_19_3 doRetrievePrenotazioneByCapsulaAndDataInizio: Tutto presente
    @Test
    void TC8_19_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioneByCapsulaAndDataInizio(1, "2025-06-01").size());
    }

    // TC8_20_1 doRetrievePrenotazioneByCapsulaAndDataFine: Nessuna data <=
    @Test
    void TC8_20_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataFine(1, "2000-01-01").isEmpty());
    }

    // TC8_20_2 doRetrievePrenotazioneByCapsulaAndDataFine: NumeroCapsulaSelect non presente
    @Test
    void TC8_20_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataFine(999, "2025-06-01").isEmpty());
    }

    // TC8_20_3 doRetrievePrenotazioneByCapsulaAndDataFine: Dati presenti
    @Test
    void TC8_20_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioneByCapsulaAndDataFine(1, "2025-06-10").size());
    }

    // TC8_21_1 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: Nessuna data >= inizio
    @Test
    void TC8_21_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC8_21_2 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: NumeroCapsulaSelect non presente
    @Test
    void TC8_21_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataInizioAndDataFine(999, "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC8_21_3 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: Nessuna data <= fine
    @Test
    void TC8_21_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_21_4 doRetrievePrenotazioneByCapsulaAndDataInizioAndDataFine: Dati presenti
    @Test
    void TC8_21_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2025-06-01", "2025-06-10").size());
    }

    // TC8_22_1 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Nessuna data >=
    @Test
    void TC8_22_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndDataInizioAndDataFine("user@example.com", "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC8_22_2 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Account non presente
    @Test
    void TC8_22_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndDataInizioAndDataFine("missing@example.com", "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC8_22_3 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Nessuna data <= fine
    @Test
    void TC8_22_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotazioniByAccountAndDataInizioAndDataFine("user@example.com", "2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_22_4 doRetrievePrenotazioniByAccountAndDataInizioAndDataFine: Dati presenti
    @Test
    void TC8_22_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        assertEquals(1, dao.doRetrivePrenotazioniByAccountAndDataInizioAndDataFine("user@example.com", "2025-06-01", "2025-06-10").size());
    }
    
    /*
    @AfterEach
    void tearDown() throws Exception {
        verify(mockPreparedStatement, atLeastOnce()).close();
        verify(mockConnection, atLeastOnce()).close();
    }*/

}
