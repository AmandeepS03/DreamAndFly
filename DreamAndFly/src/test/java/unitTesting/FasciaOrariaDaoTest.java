package unitTesting;


import org.junit.jupiter.api.AfterEach;
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

    // TC7_6.1 - doRetrieveByKey: fascia non presente
    @Test
    void TC7_1_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        FasciaOraria result = dao.doRetrieveByKey(999);

        assertEquals(0, result.getNumero());
        assertNull(result.getorarioInizio());
        assertNull(result.getorarioFine());
        verify(mockPreparedStatement).setInt(1, 999);
    }

    // TC7_1.2 - doRetrieveByKey: fascia presente
    @Test
    void TC7_1_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(1);
        when(mockResultSet.getString("orario_inizio")).thenReturn("00:00");
        when(mockResultSet.getString("orario_fine")).thenReturn("01:00");

        FasciaOraria result = dao.doRetrieveByKey(1);

        assertEquals(1, result.getNumero());
        assertEquals("00:00", result.getorarioInizio());
        assertEquals("01:00", result.getorarioFine());
        verify(mockPreparedStatement).setInt(1, 1);
    }

    

    // TC7.2.1 - doRetrieveByOrarioInizio: orario non presente
    @Test
    void TC7_2_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        int result = dao.doRetrieveByOrarioInizio("25:00");

        assertEquals(0, result);
        verify(mockPreparedStatement).setString(1, "25:00");
    }

    // TC7_2_2 - doRetrieveByOrarioInizio: orario presente
    @Test
    void TC7_2_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(13);

        int result = dao.doRetrieveByOrarioInizio("12:00");

        assertEquals(13, result);
        verify(mockPreparedStatement).setString(1, "12:00");
    }

    // TC7_3_1 - doRetrieveByOrarioFine: orario non presente
    @Test
    void TC7_3_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        int result = dao.doRetrieveByOrarioFine("99:00");

        assertEquals(0, result);
        verify(mockPreparedStatement).setString(1, "99:00");
    }

    // TC7_3_2 - doRetrieveByOrarioFine: orario presente
    @Test
    void TC7_3_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(23);

        int result = dao.doRetrieveByOrarioFine("23:00");

        assertEquals(23, result);
        verify(mockPreparedStatement).setString(1, "23:00");
    }
    
    @AfterEach
    void tearDown() throws Exception {
        // Verifica la chiusura delle risorse comuni
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }
}
