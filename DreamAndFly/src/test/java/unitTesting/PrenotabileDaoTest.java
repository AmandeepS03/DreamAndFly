
package unitTesting;

import org.junit.jupiter.api.*;
import storage.Prenotabile;
import storage.PrenotabileDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    // TC8_1_1 - doSave: Oggetto non presente
    @Test
    void TC8_1_1() throws Exception {
        Prenotabile pren = new Prenotabile();
        pren.setDataPrenotabile("2026-12-31");
        pren.setCapsulaId(5);
        pren.setFasciaOrariaNumero(1);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doSave(pren);

        verify(mockPreparedStatement).setString(1, "2026-12-31");
        verify(mockPreparedStatement).setInt(2, 5);
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_1_2 - doSave - Oggetto già presente
    @Test
    void TC8_1_2() throws Exception {
        // Simula una SQLException per violazione di vincolo (es. riga già presente)
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Duplicate entry"));

        Prenotabile prenotabile = new Prenotabile();
        prenotabile.setDataPrenotabile("2026-05-10");
        prenotabile.setCapsulaId(5);
        prenotabile.setFasciaOrariaNumero(1);

        // Verifica che venga lanciata una SQLException
        assertThrows(SQLException.class, () -> dao.doSave(prenotabile));

        // Verifica che executeUpdate sia stato chiamato
        verify(mockPreparedStatement).executeUpdate();
    }


 // TC8_2_1 - doDelete: data non presente
    @Test
    void TC8_2_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doDelete("2099-01-01", 1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_2_2 - doDelete: capsula_id non presente
    @Test
    void TC8_2_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doDelete("2025-06-01", 99, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_2_3 - doDelete: fascia oraria non presente
    @Test
    void TC8_2_3() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        dao.doDelete("2025-06-01", 1, 99);
        verify(mockPreparedStatement).executeUpdate();
    }

    // TC8_2_4 - doDelete: tutti i dati presenti
    @Test
    void TC8_2_4() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao.doDelete("2026-12-30", 6, 2);
        verify(mockPreparedStatement).executeUpdate();
    }


    // TC8_3_1 - doRetrieveLastDateById: id non presente
    @Test
    void TC8_3_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        Prenotabile result = dao.doRetrieveLastDateById(99);
        assertNotNull(result);
        assertNull(result.getDataPrenotabile());
    }

    // TC8_3_2 - doRetrieveLastDateById : id presente
    @Test
    void TC8_3_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2026-06-11");
        when(mockResultSet.getInt("capsula_id")).thenReturn(6);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);
        Prenotabile result = dao.doRetrieveLastDateById(6);
        assertEquals("2026-06-11", result.getDataPrenotabile());
    }

    // TC8_4_1 - doRetrieveIdByDataInizioAndDataFine: Nessuna data >= dataInizio è presente nel DB.
    @Test
    void TC8_4_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveIdByDataInizioDataFine("2099-01-01", "2099-01-31").isEmpty());
    }

    // TC8_4_2 - doRetrieveByDataInizioDataFine: Nessuna data <= dataFine è presente nel DB.
    @Test
    void TC8_4_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveIdByDataInizioDataFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_4_3 - doRetrieveByDataInizioDataFine: Date presenti nel DB
    @Test
    void TC8_4_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        assertEquals(1, dao.doRetrieveIdByDataInizioDataFine("2025-06-01", "2025-06-05").size());
    }

    // TC8_5_1 - doRetrieveByIdAndDate: capsula_id non presente
    @Test
    void TC8_5_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndDate(999, "2025-06-01"));
    }

    // TC8_5_2 - doRetrieveByIdAndDate: data non presente nel DB
    @Test
    void TC8_5_2() throws Exception {
        // Simuliamo che il ResultSet non abbia risultati (data non presente)
        when(mockResultSet.next()).thenReturn(false);

        // Eseguiamo il metodo con un ID valido e una data che non esiste
        boolean result = dao.doRetrieveByIdAndDate(1, "2099-12-31");

        // Ci aspettiamo che il metodo ritorni false
        assertFalse(result);

        // Verifica che la query sia stata eseguita
        verify(mockPreparedStatement).executeQuery();
    }


    // TC8_5_3 - doRetrieveByIdAndDate: entrambi presenti
    @Test
    void TC8_5_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        assertTrue(dao.doRetrieveByIdAndDate(1, "2025-06-01"));
    }

    @AfterEach
    void tearDown() throws Exception {
        verify(mockPreparedStatement, atLeastOnce()).close();
        verify(mockConnection, atLeastOnce()).close();
    }


    // TC8_6_1 - doRetrieveByIdAndFasciaOrariaAndDate: capsula_id non presente
    @Test
    void TC8_6_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndFasciaOrariaAndDate(999, 2, "2025-06-01"));
    }

    // TC8_6_2 - doRetrieveByIdAndFasciaOrariaAndDate: data non presente nel DB
    @Test
    void TC8_6_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 2, "2099-12-31"));
    }

    // TC8_6_3 - doRetrieveByIdAndFasciaOrariaAndDate: fascia_oraria non presente
    @Test
    void TC8_6_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertFalse(dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 999, "2025-06-01"));
    }

    // TC8_6_4 - doRetrieveByIdAndFasciaOrariaAndDate: tutti i dati presenti nel DB
    @Test
    void TC8_6_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        assertTrue(dao.doRetrieveByIdAndFasciaOrariaAndDate(1, 2, "2025-06-01"));
    }

    // TC8_7_1 -doRetrieveById:  capsula_id non presente
    @Test
    void TC8_7_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveById(100).isEmpty());
    }

    // TC8_7_2 - doRetrieveById: capsula_id presente
    @Test
    void TC8_7_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2026-05-10");
        when(mockResultSet.getInt("capsula_id")).thenReturn(5);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        assertEquals(1, dao.doRetrieveById(5).size());
    }



    // TC8_8_1 - doRetrieveByDataInizio: Nessuna data >= di quella fornita
    @Test
    void TC8_8_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizio("2099-12-31").isEmpty());
    }

    // TC8_8_2 - doRetrieveByDataInizio: Esiste almeno una data >= di quella fornita
    @Test
    void TC8_8_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2026-05-10");
        when(mockResultSet.getInt("capsula_id")).thenReturn(2);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);
        assertEquals(1, dao.doRetrieveByDataInizio("2026-05-10").size());
    }

    // TC8_9_1 - doRetrievePrenotabiliByCapsulaAndDataInizio: capsula_id non presente
    @Test
    void TC8_9_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabiliByCapsulaAndDataInizio(999, "2025-06-01").isEmpty());
    }

    // TC8_9_2 - doRetrievePrenotabiliByCapsulaAndDataInizio: Nessuna data >= dataInizio
    @Test
    void TC8_9_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabiliByCapsulaAndDataInizio(1, "2099-12-31").isEmpty());
    }

    // TC8_9_3 - doRetrievePrenotabiliByCapsulaAndDataInizio: Tutti i dati presenti
    @Test
    void TC8_9_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-03");
        when(mockResultSet.getInt("capsula_id")).thenReturn(4);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        assertEquals(1, dao.doRetrievePrenotabiliByCapsulaAndDataInizio(4, "2025-06-01").size());
    }

    // TC8_10_1 -doRetrievePrenotabiliByCapsulaAndDataFine: capsula_id non presente
    @Test
    void TC8_10_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabiliByCapsulaIdAndDataFine(999, "2025-06-01").isEmpty());
    }

    // TC8_10_2 -doRetrievePrenotabiliByCapsulaAndDataFine:  Nessuna data <= dataFine è presente nel DB
    @Test
    void TC8_10_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabiliByCapsulaIdAndDataFine(1, "2000-01-01").isEmpty());
    }

    // TC8_10_3 - doRetrievePrenotabiliByCapsulaAndDataFine: Tutti presenti
    @Test
    void TC8_10_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-05-30");
        when(mockResultSet.getInt("capsula_id")).thenReturn(5);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(1);
        assertEquals(1, dao.doRetrievePrenotabiliByCapsulaIdAndDataFine(5, "2025-06-01").size());
    }

    // TC8_11_1 -  doRetrieveByDataFine : Nessuna data <= di quella fornita è presente nel DB
    @Test
    void TC8_11_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataFine("1999-01-01").isEmpty());
    }

    // TC8_11_2 - doRetrieveByDataFine: Esiste almeno una data <= fornita è presente nel DB
    @Test
    void TC8_11_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2026-05-10");
        when(mockResultSet.getInt("capsula_id")).thenReturn(3);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(3);
        assertEquals(1, dao.doRetrieveByDataFine("2026-05-10").size());
    }

    // TC8_12_1 - doRetrieveByDataInizioAndDataFine: Nessuna data >= dataInizio
    @Test
    void TC8_12_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioAndDataFine("2099-01-01", "2099-01-10").isEmpty());
    }

    // TC8_12_2 - doRetrieveByDataInizioAndDataFine: Nessuna data <= dataFine
    @Test
    void TC8_12_2() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrieveByDataInizioAndDataFine("2025-01-01", "2025-01-02").isEmpty());
    }

    // TC8_12_3 - doRetrieveByDataInizioAndDataFine: Almeno una data valida nell'intervallo
    @Test
    void TC8_12_3() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-02");
        when(mockResultSet.getInt("capsula_id")).thenReturn(1);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);
        assertEquals(1, dao.doRetrieveByDataInizioAndDataFine("2025-06-01", "2025-06-05").size());
    }

    // TC8_13_1 - doRetrievePrenotabileByCapsulaAndDataInizioAndDataFine: capsula_id non presente
    @Test
    void TC8_13_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabileByCapsulaIdAndDataInizioAndDataFine(999, "2025-06-01", "2025-06-10").isEmpty());
    }

    // TC8_13_2 - doRetrievePrenotabileByCapsulaAndDataInizioAndDataFine: Nessuna data >= di quella fornita è presente DB.
    @Test
    void TC8_13_2 () throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabileByCapsulaIdAndDataInizioAndDataFine(1, "2099-01-01", "2099-12-31").isEmpty());
    }

    // TC8_13_3 - doRetrievePrenotabileByCapsulaAndDataInizioAndDataFine: Nessuna data <= di quella fornita è presente DB.
    @Test
    void TC8_13_3() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        assertTrue(dao.doRetrievePrenotabileByCapsulaIdAndDataInizioAndDataFine(1, "2025-01-01", "2025-01-02").isEmpty());
    }




    // TC8_13_4 - doRetrievePrenotabileByCapsulaAndDataInizioAndDataFine: capsula_id presente nel DB ed esiste almeno una data che rientra nell’intervallo. 
    @Test
    void TC8_13_4() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("data_prenotabile")).thenReturn("2025-06-02");
        when(mockResultSet.getInt("capsula_id")).thenReturn(6);
        when(mockResultSet.getInt("fascia_oraria_numero")).thenReturn(2);

        Collection<Prenotabile> result = dao.doRetrievePrenotabileByCapsulaIdAndDataInizioAndDataFine(
            6, "2025-06-01", "2025-06-10");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(mockPreparedStatement).setInt(1, 6);
        verify(mockPreparedStatement).setString(2, "2025-06-01");
        verify(mockPreparedStatement).setString(3, "2025-06-10");
        verify(mockPreparedStatement).executeQuery();
    }



}
