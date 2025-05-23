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
        when(mockResultSet.getInt("numero")).thenReturn(3);
        when(mockResultSet.getString("orario_inizio")).thenReturn("08:00");
        when(mockResultSet.getString("orario_fine")).thenReturn("09:00");

        FasciaOraria result = dao.doRetrieveByKey(3);

        assertEquals(3, result.getNumero());
        assertEquals("08:00", result.getorarioInizio());
        assertEquals("09:00", result.getorarioFine());
        verify(mockPreparedStatement).setInt(1, 3);
    }

    

    // TC7.2.1 - doRetrieveByOrarioInizio: orario non presente
    @Test
    void TC7_2_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        int result = dao.doRetrieveByOrarioInizio("07:00");

        assertEquals(0, result);
        verify(mockPreparedStatement).setString(1, "07:00");
    }

    // TC7_2_2 - doRetrieveByOrarioInizio: orario presente
    @Test
    void TC7_2_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(2);

        int result = dao.doRetrieveByOrarioInizio("09:00");

        assertEquals(2, result);
        verify(mockPreparedStatement).setString(1, "09:00");
    }

    // TC7_3_1 - doRetrieveByOrarioFine: orario non presente
    @Test
    void TC7_3_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        int result = dao.doRetrieveByOrarioFine("18:00");

        assertEquals(0, result);
        verify(mockPreparedStatement).setString(1, "18:00");
    }

    // TC7_3_2 - doRetrieveByOrarioFine: orario presente
    @Test
    void TC7_3_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("numero")).thenReturn(4);

        int result = dao.doRetrieveByOrarioFine("12:00");

        assertEquals(4, result);
        verify(mockPreparedStatement).setString(1, "12:00");
    }
    
    @AfterEach
    void tearDown() throws Exception {
        // Verifica la chiusura delle risorse comuni
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }
}
