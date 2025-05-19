package ciao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.Capsula;
import storage.CapsulaDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    
    // TC5_1.1 - doRetrieveByKey: ID non presente
    @Test
    void testTC5_1_idNonPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        Capsula result = dao.doRetrieveByKey(999);

         assertNull(result.getTipologia());
        assertEquals(0.0f, result.getPrezzo_orario());

        verify(mockPreparedStatement).setInt(1, 999);
        verify(mockPreparedStatement).executeQuery();
    }

    // TC5_1.2 - doRetrieveByKey: ID presente
    @Test
    void testTC5_1_idPresente() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getFloat("prezzo_orario")).thenReturn(19.99f);
        when(mockResultSet.getString("tipologia")).thenReturn("Standard");

        Capsula result = dao.doRetrieveByKey(1);

        assertEquals(1, result.getId());
        assertEquals(19.99f, result.getPrezzo_orario());
        assertEquals("Standard", result.getTipologia());

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeQuery();
    }

    // TC5_2.1 - doUpdatePrezzoOrario: ID non presente
    @Test
    void testTC5_2_idNonPresente() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // nessuna riga modificata

        dao.doUpdatePrezzoOrario(999, 25.0f);

        verify(mockPreparedStatement).setFloat(1, 25.0f);
        verify(mockPreparedStatement).setInt(2, 999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC5_2.2 - doUpdatePrezzoOrario: ID presente
    @Test
    void testTC5_2_idPresente() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // una riga modificata

        dao.doUpdatePrezzoOrario(2, 22.5f);

        verify(mockPreparedStatement).setFloat(1, 22.5f);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC5_3.1 - doSave: capsula non presente (simulazione: insert riuscito)
    @Test
    void testTC5_3_capsulaNonPresente() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // inserimento riuscito

        Capsula capsula = new Capsula();
        capsula.setId(5);
        capsula.setPrezzo_orario(30.0f);
        capsula.setTipologia("Deluxe");

        dao.doSave(capsula);

        verify(mockPreparedStatement).setInt(1, 5);
        verify(mockPreparedStatement).setFloat(2, 30.0f);
        verify(mockPreparedStatement).setString(3, "Deluxe");
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC5_3.2 - doSave: capsula già presente (simulazione: insert fallito)
    @Test
    void testTC5_3_capsulaGiaPresente() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // simuliamo che non venga inserita

        Capsula capsula = new Capsula();
        capsula.setId(1);
        capsula.setPrezzo_orario(19.99f);
        capsula.setTipologia("Standard");

        dao.doSave(capsula);

        verify(mockPreparedStatement).executeUpdate();
    }
}
