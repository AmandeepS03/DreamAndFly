package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Prenotabile;
import storage.PrenotabileDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrenotabileDaoTest {

    private DataSource mockDataSource;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private PrenotabileDao dao;

    @BeforeEach
    void setUp() throws Exception {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new PrenotabileDao(mockDataSource);
    }

    @Test
    void testDoSavePrenotabile() throws Exception {
        Prenotabile pren = new Prenotabile();
        pren.setDataPrenotabile("2025-06-01");
        pren.setCapsulaId(1);
        pren.setFasciaOrariaNumero(2);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doSave(pren);

        verify(mockPreparedStatement).setString(1, "2025-06-01");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).setInt(3, 2);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoDelete_TuttiPresenti() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doDelete("2025-06-01", 1, 2);

        verify(mockPreparedStatement).setString(1, "2025-06-01");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).setInt(3, 2);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoRetrieveLastDateById_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        Prenotabile result = dao.doRetrieveLastDateById(999);
        assertNull(result.getDataPrenotabile());
        assertEquals(0, result.getCapsulaId());
    }

    @Test
    void testDoRetrieveLastDateById_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-05");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Prenotabile result = dao.doRetrieveLastDateById(1);

        assertEquals("2025-06-05", result.getDataPrenotabile());
        assertEquals(1, result.getCapsulaId());
        assertEquals(2, result.getFasciaOrariaNumero());
    }

    @Test
    void testDoRetrieveByIdAndDate_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        boolean result = dao.doRetrieveByIdAndDate(1, "2025-06-01");
        assertTrue(result);
    }

    @Test
    void testDoRetrieveByIdAndDate_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        boolean result = dao.doRetrieveByIdAndDate(1, "2025-06-01");
        assertFalse(result);
    }

    @Test
    void testDoRetrieveByIdAndFasciaOrariaAndDate_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        boolean result = dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 2, "2025-06-01");
        assertTrue(result);
    }

    @Test
    void testDoRetrieveByIdAndFasciaOrariaAndDate_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        boolean result = dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 2, "2025-06-01");
        assertFalse(result);
    }

    @Test
    void testDoRetrieveByDataInizioAndDataFine_DateInRange() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);

        Collection<Integer> result = dao.doRetrieveByDataInizioDataFine("2025-06-01", "2025-06-05");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrieveByDataInizioAndDataFine_Empty() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Collection<Integer> result = dao.doRetrieveByDataInizioDataFine("2025-06-01", "2025-06-05");
        assertTrue(result.isEmpty());
    }

    @Test
    void testDoRetrieveById_ReturnsList() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-01");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Collection<Prenotabile> result = dao.doRetrieveById(1);
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrieveByDataInizioAndDataFine_Range() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-02");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Collection<Prenotabile> result = dao.doRetrieveByDataInizioAndDataFine("2025-06-01", "2025-06-05");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrieveByDataInizio() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-10");
        when(mockResultSet.getInt("capsula_id")).thenReturn(2);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);

        Collection<Prenotabile> result = dao.doRetrieveByDataInizio("2025-06-01");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrieveByDataFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-01");
        when(mockResultSet.getInt("capsula_id")).thenReturn(3);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(3);

        Collection<Prenotabile> result = dao.doRetrieveByDataFine("2025-06-10");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotabiliByCapsulaAndDataInizio() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-03");
        when(mockResultSet.getInt("capsula_id")).thenReturn(4);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Collection<Prenotabile> result = dao.doRetrivePrenotabiliByCapsulaAndDataInizio(4, "2025-06-01");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotabiliByCapsulaAndDataFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-05-30");
        when(mockResultSet.getInt("capsula_id")).thenReturn(5);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);

        Collection<Prenotabile> result = dao.doRetrivePrenotabiliByCapsulaAndDataFine(5, "2025-06-01");
        assertEquals(1, result.size());
    }

    @Test
    void testDoRetrievePrenotabiliByCapsulaAndDataInizioAndFine() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-02");
        when(mockResultSet.getInt("capsula_id")).thenReturn(6);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Collection<Prenotabile> result = dao.doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine(6, "2025-06-01", "2025-06-10");
        assertEquals(1, result.size());
    }
} 

