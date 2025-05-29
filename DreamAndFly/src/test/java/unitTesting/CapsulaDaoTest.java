package unitTesting;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.Capsula;
import storage.CapsulaDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CapsulaDaoTest {

    private DataSource mockDataSource;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private CapsulaDao dao;

    @BeforeEach
    void setUp() throws Exception {
        mockDataSource        = mock(DataSource.class);
        mockConnection        = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet         = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new CapsulaDao(mockDataSource);
    }

    
    // TC6_1.1 - doRetrieveByKey: ID non presente
    @Test
    void TC6_1_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        Capsula result = dao.doRetrieveByKey(999);

         assertNull(result.getTipologia());
        assertEquals(0.0f, result.getPrezzo_orario());

        verify(mockPreparedStatement).setInt(1, 999);
        verify(mockPreparedStatement).executeQuery();
    }

    // TC6_1.2 - doRetrieveByKey: ID presente
    @Test
    void TC6_1_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getFloat("prezzo_orario")).thenReturn(9.0f);
        when(mockResultSet.getString("tipologia")).thenReturn("Deluxe");

        Capsula result = dao.doRetrieveByKey(1);

        assertEquals(1, result.getId());
        assertEquals(9.0f, result.getPrezzo_orario());
        assertEquals("Deluxe", result.getTipologia());

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeQuery();
    }

    // TC6_2.1 - doUpdatePrezzoOrario: ID non presente
    @Test
    void TC6_2_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // nessuna riga modificata

        dao.doUpdatePrezzoOrario(999, 15.0f);

        verify(mockPreparedStatement).setFloat(1, 15.0f);
        verify(mockPreparedStatement).setInt(2, 999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC6_2.2 - doUpdatePrezzoOrario: ID presente
    @Test
    void TC6_2_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // una riga modificata

        dao.doUpdatePrezzoOrario(2, 12.5f);

        verify(mockPreparedStatement).setFloat(1, 12.5f);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeUpdate();
    }

    //TODO
    // TC6_3.1 - doSave: capsula non presente (simulazione: insert riuscito)
    @Test
    void TC6_3_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // inserimento riuscito

        Capsula capsula = new Capsula();
        capsula.setId(999);
        capsula.setPrezzo_orario(20.0f);
        capsula.setTipologia("Deluxe");

        dao.doSave(capsula);

        verify(mockPreparedStatement).setInt(1, 999);
        verify(mockPreparedStatement).setFloat(2, 20.0f);
        verify(mockPreparedStatement).setString(3, "Deluxe");
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC6_3.2 - doSave: capsula già presente (simulazione: insert fallito)
    @Test
    void TC6_3_2() throws Exception {
        // Simuliamo una violazione di vincolo (es. chiave primaria già esistente)
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Duplicate entry"));

        Capsula capsula = new Capsula();
        capsula.setId(1); // Supponiamo che l'ID 1 sia già presente nel DB
        capsula.setPrezzo_orario(15.0f);
        capsula.setTipologia("Standard");

        // Verifica che venga lanciata una SQLException
        assertThrows(SQLException.class, () -> dao.doSave(capsula));

        // Verifica che executeUpdate sia stato chiamato
        verify(mockPreparedStatement).executeUpdate();
    }

    
    
    @AfterEach
    void tearDown() throws Exception {
        // Verifica la chiusura delle risorse comuni
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    
}
