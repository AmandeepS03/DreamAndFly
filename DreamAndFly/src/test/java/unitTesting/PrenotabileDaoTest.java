
package unitTesting;

import org.junit.jupiter.api.*;
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

    // TC7_1_1 - doSave: Oggetto non presente
    @Test
    void TC7_1_1() throws Exception {
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

    // TC7_1_2 - doSave - Oggetto già presente
    @Test
    void TC7_1_2() throws Exception {
        Prenotabile pren = new Prenotabile();
        pren.setDataPrenotabile("2025-06-01");
        pren.setCapsulaId(1);
        pren.setFasciaOrariaNumero(2);

        when(mockPreparedStatement.executeUpdate()).thenThrow(new java.sql.SQLIntegrityConstraintViolationException());
        assertThrows(java.sql.SQLIntegrityConstraintViolationException.class, () -> dao.doSave(pren));
    }

 // TC7_2_1 - doDelete: data non presente
    @Test
    void TC7_2_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doDelete("2099-12-31", 1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC7_2_2 - doDelete: capsula_id non presente
    @Test
    void TC7_2_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doDelete("2025-06-01", 9999, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC7_2_3 - doDelete: fascia oraria non presente
    @Test
    void TC7_2_3() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doDelete("2025-06-01", 1, 9999);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC7_2_4 - doDelete: tutti i dati presenti
    @Test
    void TC7_2_4() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doDelete("2025-06-01", 1, 2);
        verify(mockPreparedStatement).executeUpdate();
    }


    // TC7_3_1 - doRetrieveLastDateById: id non presente
    @Test
    void TC7_3_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Prenotabile result = dao.doRetrieveLastDateById(999);
        assertNotNull(result);
        assertNull(result.getDataPrenotabile());
    }

    // TC7_3_2 - doRetrieveLastDateById : id presente
    @Test
    void TC7_3_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-05");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        Prenotabile result = dao.doRetrieveLastDateById(1);
        assertEquals("2025-06-05", result.getDataPrenotabile());
    }

    // TC7_4_1 - doRetrieveByDataInizioDataFine: Nessuna data >= dataInizio è presente nel DB.
    @Test
    void TC7_4_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioDataFine("2099-01-01", "2099-01-31").isEmpty());
    }

    // TC7_4_2 - doRetrieveByDataInizioDataFine: Nessuna data <= dataFine è presente nel DB.
    @Test
    void TC7_4_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioDataFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC7_4_3 - doRetrieveByDataInizioDataFine: Date presenti nel DB
    @Test
    void TC7_4_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        assertEquals(1, dao.doRetrieveByDataInizioDataFine("2025-06-01", "2025-06-05").size());
    }

    // TC7_5_1 - doRetrieveByIdAndDate: capsula_id non presente
    @Test
    void TC7_5_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndDate(999, "2025-06-01"));
    }

    // TC7_5_2 - doRetrieveByIdAndDate: data non presente nel DB
    @Test
    void TC7_5_2() throws Exception {
        // Simuliamo che il ResultSet non abbia risultati (data non presente)
        when(mockResultSet.next()).thenReturn(false);

        // Eseguiamo il metodo con un ID valido e una data che non esiste
        boolean result = dao.doRetrieveByIdAndDate(1, "2099-12-31");

        // Ci aspettiamo che il metodo ritorni false
        assertFalse(result);

        // Verifica che la query sia stata eseguita
        verify(mockPreparedStatement).executeQuery();
    }


    // TC7_5_3 - doRetrieveByIdAndDate: entrambi presenti
    @Test
    void TC7_5_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        assertTrue(dao.doRetrieveByIdAndDate(1, "2025-06-01"));
    }

    @AfterEach
    void tearDown() throws Exception {
        verify(mockPreparedStatement, atLeastOnce()).close();
        verify(mockConnection, atLeastOnce()).close();
    }


    // TC7_6_1 - doRetrieveByIdAndFasciaOrariaAndDate: capsula_id non presente
    @Test
    void TC7_6_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndFasciaOrariaAndDate(999, 2, "2025-06-01"));
    }

    // TC7_6_2 - doRetrieveByIdAndFasciaOrariaAndDate: data non presente nel DB
    @Test
    void TC7_6_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 2, "2099-12-31"));
    }

    // TC7_6_3 - doRetrieveByIdAndFasciaOrariaAndDate: fascia_oraria non presente
    @Test
    void TC7_6_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 999, "2025-06-01"));
    }

    // TC7_6_4 - doRetrieveByIdAndFasciaOrariaAndDate: tutti i dati presenti nel DB
    @Test
    void TC7_6_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        assertTrue(dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 2, "2025-06-01"));
    }

    // TC7_7_1 -doRetrieveById:  capsula_id non presente
    @Test
    void TC7_7_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveById(999).isEmpty());
    }

    // TC7_7_2 - doRetrieveById: capsula_id presente
    @Test
    void TC7_7_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-01");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        assertEquals(1, dao.doRetrieveById(1).size());
    }



    // TC7_8_1 - doRetrieveByDataInizio: Nessuna data >= di quella fornita
    @Test
    void TC7_8_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizio("2099-12-31").isEmpty());
    }

    // TC7_8_2 - doRetrieveByDataInizio: Esiste almeno una data >= di quella fornita
    @Test
    void TC7_8_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-10");
        when(mockResultSet.getInt("capsula_id")).thenReturn(2);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);
        assertEquals(1, dao.doRetrieveByDataInizio("2025-06-01").size());
    }

    // TC7_9_1 - doRetrivePrenotabiliByCapsulaAndDataInizio: capsula_id non presente
    @Test
    void TC7_9_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabiliByCapsulaAndDataInizio(999, "2025-06-01").isEmpty());
    }

    // TC7_9_2 - doRetrivePrenotabiliByCapsulaAndDataInizio: Nessuna data >= dataInizio
    @Test
    void TC7_9_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabiliByCapsulaAndDataInizio(1, "2099-12-31").isEmpty());
    }

    // TC7_9_3 - doRetrivePrenotabiliByCapsulaAndDataInizio: Tutti i dati presenti
    @Test
    void TC7_9_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-03");
        when(mockResultSet.getInt("capsula_id")).thenReturn(4);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        assertEquals(1, dao.doRetrivePrenotabiliByCapsulaAndDataInizio(4, "2025-06-01").size());
    }

    // TC7_10_1 -doRetrivePrenotabiliByCapsulaAndDataFine: capsula_id non presente
    @Test
    void TC7_10_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabiliByCapsulaAndDataFine(999, "2025-06-01").isEmpty());
    }

    // TC7_10_2 -doRetrivePrenotabiliByCapsulaAndDataFine:  Nessuna data <= dataFine è presente nel DB
    @Test
    void TC7_10_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabiliByCapsulaAndDataFine(1, "2000-01-01").isEmpty());
    }

    // TC7_10_3 - doRetrivePrenotabiliByCapsulaAndDataFine: Tutti presenti
    @Test
    void TC7_10_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-05-30");
        when(mockResultSet.getInt("capsula_id")).thenReturn(5);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);
        assertEquals(1, dao.doRetrivePrenotabiliByCapsulaAndDataFine(5, "2025-06-01").size());
    }

    // TC7_11_1 -  doRetrieveByDataFine : Nessuna data <= di quella fornita è presente nel DB
    @Test
    void TC7_11_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataFine("2000-01-01").isEmpty());
    }

    // TC7_11_2 - doRetrieveByDataFine: Esiste almeno una data <= fornita è presente nel DB
    @Test
    void TC7_11_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-01");
        when(mockResultSet.getInt("capsula_id")).thenReturn(3);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(3);
        assertEquals(1, dao.doRetrieveByDataFine("2025-06-10").size());
    }

    // TC7_12_1 - doRetrieveByDataInizioAndDataFine: Nessuna data >= dataInizio
    @Test
    void TC7_12_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioAndDataFine("2099-01-01", "2099-01-10").isEmpty());
    }

    // TC7_12_2 - doRetrieveByDataInizioAndDataFine: Nessuna data <= dataFine
    @Test
    void TC7_12_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioAndDataFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC7_12_3 - doRetrieveByDataInizioAndDataFine: Almeno una data valida nell'intervallo
    @Test
    void TC7_12_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-02");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        assertEquals(1, dao.doRetrieveByDataInizioAndDataFine("2025-06-01", "2025-06-05").size());
    }

    // TC7_13_1 - doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine: capsula_id non presente
    @Test
    void TC7_13_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine(999, "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC7_13_2 - doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine: Nessuna data >= di quella fornita è presente DB.
    @Test
    void TC7_13_2 () throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine(1, "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC7_13_3 - doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine: Nessuna data <= di quella fornita è presente DB.
    @Test
    void TC7_13_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine(1, "2025-01-01", "2025-01-02").isEmpty());
    }




    // TC7_13_4 - doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine: capsula_id presente nel DB ed esiste almeno una data che rientra nell’intervallo. 
    @Test
    void TC7_13_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-02");
        when(mockResultSet.getInt("capsula_id")).thenReturn(6);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Collection<Prenotabile> result = dao.doRetrivePrenotabileByCapsulaAndDataInizioAndDataFine(
            6, "2025-06-01", "2025-06-10");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(mockPreparedStatement).setInt(1, 6);
        verify(mockPreparedStatement).setString(2, "2025-06-01");
        verify(mockPreparedStatement).setString(3, "2025-06-10");
        verify(mockPreparedStatement).executeQuery();
    }



}
