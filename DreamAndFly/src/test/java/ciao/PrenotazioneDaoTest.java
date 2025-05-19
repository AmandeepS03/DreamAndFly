package ciao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Prenotazione;
import storage.PrenotazioneDao;
import utils.PrenotazioneWrapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

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

    @Test
    void testDoRetrieveByKey_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Prenotazione result = dao.doRetrieveByKey(999);
        assertNull(result.getUserAccountEmail());
    }

    @Test
    void testDoRetrieveByKey_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("codice_di_accesso")).thenReturn(1);
        when(mockResultSet.getString("orario_inizio")).thenReturn("08:00");
        when(mockResultSet.getString("orario_fine")).thenReturn("10:00");
        when(mockResultSet.getString("data_inizio")).thenReturn("2025-06-10");
        when(mockResultSet.getString("data_fine")).thenReturn("2025-06-12");
        when(mockResultSet.getFloat("prezzo_totale")).thenReturn(45.5f);
        when(mockResultSet.getString("data_effettuazione")).thenReturn("2025-05-01");
        when(mockResultSet.getBoolean("validita")).thenReturn(true);
        when(mockResultSet.getFloat("rimborso")).thenReturn(5.0f);
        when(mockResultSet.getString("user_account_email")).thenReturn("test@example.com");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);

        Prenotazione result = dao.doRetrieveByKey(1);

        assertEquals(1, result.getCodiceDiAccesso());
        assertEquals("test@example.com", result.getUserAccountEmail());
        assertEquals("08:00", result.getOrarioInizio());
        assertEquals("10:00", result.getOrarioFine());
        assertEquals("2025-06-10", result.getDataInizio());
        assertEquals("2025-06-12", result.getDataFine());
        assertEquals(45.5f, result.getPrezzoTotale());
        assertEquals("2025-05-01", result.getDataEffettuazione());
        assertEquals(true, result.isValidita());
        assertEquals(5.0f, result.getRimborso());
        assertEquals(1, result.getCapsulaId());
    }


    @Test
    void testDoSave_NewPrenotazione() throws Exception {
        Prenotazione p = mock(Prenotazione.class);
        when(p.getOrarioInizio()).thenReturn("08:00");
        when(p.getOrarioFine()).thenReturn("10:00");
        when(p.getDataInizio()).thenReturn("2025-06-10");
        when(p.getDataFine()).thenReturn("2025-06-12");
        when(p.getPrezzoTotale()).thenReturn(45.5f);
        when(p.getDataEffettuazione()).thenReturn("2025-05-01");
        when(p.getUserAccountEmail()).thenReturn("nuova@example.com");
        when(p.getCapsulaId()).thenReturn(1);

        when(mockConnection.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(123);

        int codiceAccesso = dao.doSave(p);

        assertEquals(123, codiceAccesso);
        verify(mockPreparedStatement).setString(1, "08:00");
        verify(mockPreparedStatement).setString(2, "10:00");
        verify(mockPreparedStatement).setString(3, "2025-06-10");
        verify(mockPreparedStatement).setString(4, "2025-06-12");
        verify(mockPreparedStatement).setFloat(5, 45.5f);
        verify(mockPreparedStatement).setString(6, "2025-05-01");
        verify(mockPreparedStatement).setString(7, "nuova@example.com");
        verify(mockPreparedStatement).setInt(8, 1);
        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    void testDoUpdateValidita_Presente() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdateValidita(5, true);
        verify(mockPreparedStatement).setBoolean(1, true);
        verify(mockPreparedStatement).setInt(2, 5);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoUpdateRimborso_Presente() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdateRimborso(7, 10.5f);
        verify(mockPreparedStatement).setFloat(1, 10.5f);
        verify(mockPreparedStatement).setInt(2, 7);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoRetrieveByDataInizio_Vuoto() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Collection<Prenotazione> result = dao.doRetrieveByDataInizio("2025-06-01");
        assertTrue(result.isEmpty());
    }

    @Test
    void testDoRetrieveByDataInizio_Pieno() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("codice_accesso")).thenReturn(10);

        Collection<Prenotazione> result = dao.doRetrieveByDataInizio("2025-06-01");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrieveByDataInizioAndDataFine_Valid() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrieveByDataInizioAndDataFine("2025-06-01", "2025-06-10");
        assertEquals(1, result.size());
    }

    /*
    @Test
    void testDeletePrenotazione() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.deletePrenotazione(8);
        verify(mockPreparedStatement).setInt(1, 8);
        verify(mockPreparedStatement).executeUpdate();
    }
*/
    @Test
    void testDoRetrieveByEmail_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Collection<PrenotazioneWrapper> result = dao.doRetriveByEmail("nonpresente@example.com", 0);
        assertTrue(result.isEmpty());
    }

    @Test
    void testDoRetrieveByEmail_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("email")).thenReturn("utente@example.com");

        Collection<PrenotazioneWrapper> result = dao.doRetriveByEmail("utente@example.com", 0);
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByNumeroCapsulaAll() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByNumeroCapsulaAll(1);
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByAccount() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByAccount("email@utente.com");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByDataInizioAndFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByDataInizioAndFine("2025-06-01", "2025-06-10");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByAccountAndIdAndDataInizio() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByAccountAndIdAndDataInizio(1, "email@user.com", "2025-06-01");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByAccountAndIdAndDataFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByAccountAndIdAndDataFine(1, "email@user.com", "2025-06-30");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByAll() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByAll(1, "email@user.com", "2025-06-01", "2025-06-30");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByCapsulaAndDataInizio() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioneByCapsulaAndDataInizio(1, "2025-06-01");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByCapsulaAndDataFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioneByCapsulaAndDataFine(1, "2025-06-30");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByCapsulaAndDataInizioAndDataFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioneByCapsulaAndDataInizioAndDataFine(1, "2025-06-01", "2025-06-30");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotazioniByAccountAndDataInizioAndDataFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        Collection<Prenotazione> result = dao.doRetrivePrenotazioniByAccountAndDataInizioAndDataFine("email@user.com", "2025-06-01", "2025-06-30");
        assertEquals(1, result.size());
    }
} 
