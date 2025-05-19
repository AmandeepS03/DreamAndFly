package ciao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.FasciaOraria;
import storage.FasciaOrariaDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FasciaOrariaDaoTest {

    private DataSource mockDataSource;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private FasciaOrariaDao dao;

    @BeforeEach
    void setUp() throws Exception {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new FasciaOrariaDao(mockDataSource);
    }

    // TC9.6.1 - doRetrieveByKey: fascia non presente
    @Test
    void testDoRetrieveByKey_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        FasciaOraria result = dao.doRetrieveByKey(999);

        assertEquals(0, result.getNumero());
        assertNull(result.getorarioInizio());
        assertNull(result.getorarioFine());
        verify(mockPreparedStatement).setInt(1, 999);
    }

    // TC9.6.2 - doRetrieveByKey: fascia presente
    @Test
    void testDoRetrieveByKey_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(3);
        when(mockResultSet.getString("orario_inizio")).thenReturn("08:00");
        when(mockResultSet.getString("orario_fine")).thenReturn("09:00");

        FasciaOraria result = dao.doRetrieveByKey(3);

        assertEquals(3, result.getNumero());
        assertEquals("08:00", result.getorarioInizio());
        assertEquals("09:00", result.getorarioFine());
        verify(mockPreparedStatement).setInt(1, 3);
    }

    // TC9.6.3 - doRetriveAll: ritorna una lista di fasce
    @Test
    void testDoRetriveAll() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("numero")).thenReturn(1, 2);
        when(mockResultSet.getString("orario_inizio")).thenReturn("08:00", "09:00");
        when(mockResultSet.getString("orario_fine")).thenReturn("09:00", "10:00");

        Collection<FasciaOraria> result = dao.doRetriveAll();

        assertEquals(2, result.size());
        verify(mockConnection).prepareStatement("select * from fascia_oraria");
    }

    // TC9.6.4 - doRetrieveByOrarioInizio: orario non presente
    @Test
    void testDoRetrieveByOrarioInizio_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        int result = dao.doRetrieveByOrarioInizio("07:00");

        assertEquals(0, result);
        verify(mockPreparedStatement).setString(1, "07:00");
    }

    // TC9.6.5 - doRetrieveByOrarioInizio: orario presente
    @Test
    void testDoRetrieveByOrarioInizio_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(2);

        int result = dao.doRetrieveByOrarioInizio("09:00");

        assertEquals(2, result);
        verify(mockPreparedStatement).setString(1, "09:00");
    }

    // TC9.6.6 - doRetrieveByOrarioFine: orario non presente
    @Test
    void testDoRetrieveByOrarioFine_NotFound() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        int result = dao.doRetrieveByOrarioFine("18:00");

        assertEquals(0, result);
        verify(mockPreparedStatement).setString(1, "18:00");
    }

    // TC9.6.7 - doRetrieveByOrarioFine: orario presente
    @Test
    void testDoRetrieveByOrarioFine_Found() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(4);

        int result = dao.doRetrieveByOrarioFine("12:00");

        assertEquals(4, result);
        verify(mockPreparedStatement).setString(1, "12:00");
    }
}
