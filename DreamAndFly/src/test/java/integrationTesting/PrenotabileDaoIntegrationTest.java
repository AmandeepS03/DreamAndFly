package integrationTesting;

import org.junit.jupiter.api.*;
import storage.Prenotabile;
import storage.PrenotabileDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PrenotabileDaoIntegrationTest {

    private static DataSource dataSource;
    private PrenotabileDao dao;
    private static ConnectionTest connessioneTest = new ConnectionTest();

    @BeforeAll
    public static void init() {
        connessioneTest.connessione();
        dataSource = connessioneTest.getDataSource();
    }

    @BeforeEach
    public void setup() {
        dao = new PrenotabileDao(dataSource);
    }

    
    // TC8_1_1_IT: doSave con oggetto non presente nel DB
    @Test
    public void TC8_1_1_IT() throws SQLException {
        Prenotabile prenotabile = new Prenotabile("2026-12-31", 5, 1); // nuova combinazione
        dao.doSave(prenotabile);

        Collection<Prenotabile> results = dao.doRetrieveById(5);
        assertTrue(results.stream().anyMatch(p ->
                "2026-12-31".equals(p.getDataPrenotabile()) && p.getFasciaOrariaNumero() == 1));

        // Cleanup
        dao.doDelete("2026-12-31", 5, 1);
    }
    
    // TC8_1_2_IT: doSave con prenotabile già esistente
    @Test
    public void TC8_1_2_IT() {
        Prenotabile prenotabile = new Prenotabile("2026-05-10", 5, 1); // già esistente
        assertThrows(SQLException.class, () -> dao.doSave(prenotabile));
    }

 

    // TC8_2_1_TC8_2_2_TC8_2_3_IT: doDelete su dato inesistente
    @Test
    public void TC8_2_1_TC8_2_2_TC8_2_3_IT() throws SQLException {
        // Deve terminare senza eccezioni
        dao.doDelete("2099-01-01", 99, 99);
    }

    // TC8_2_4_IT: doDelete di un elemento esistente
    @Test
    public void TC8_2_4_IT() throws SQLException {
        Prenotabile p = new Prenotabile("2026-12-30", 6, 2);
        dao.doSave(p);

        dao.doDelete("2026-12-30", 6, 2);
        Collection<Prenotabile> list = dao.doRetrieveById(6);
        assertTrue(list.stream().noneMatch(x -> "2026-12-30".equals(x.getDataPrenotabile()) && x.getFasciaOrariaNumero() == 2));
    }

    // TC8_3_1_IT: doRetrieveLastDateById con ID inesistente
    @Test
    public void TC8_3_1_IT() throws SQLException {
        Prenotabile p = dao.doRetrieveLastDateById(99);
        assertNull(p.getDataPrenotabile());
        assertEquals(0, p.getCapsulaId());
        assertEquals(0, p.getFasciaOrariaNumero());
    }

    // TC8_3_2_IT: doRetrieveLastDateById con ID esistente
    @Test
    public void TC8_3_2_IT() throws SQLException {
        Prenotabile p = dao.doRetrieveLastDateById(6);
        assertEquals(6, p.getCapsulaId());
        assertEquals("2026-06-11", p.getDataPrenotabile());
    }

    // TC8_7_1_IT: doRetrieveById con ID inesistente
    @Test
    public void TC8_7_1_IT() throws SQLException {
        Collection<Prenotabile> lista = dao.doRetrieveById(100);
        assertTrue(lista.isEmpty());
    }

    // TC8_7_2_IT: doRetrieveById con ID esistente
    @Test
    public void TC8_7_2_IT() throws SQLException {
        Collection<Prenotabile> lista = dao.doRetrieveById(5);
        assertFalse(lista.isEmpty());
        assertTrue(lista.stream().anyMatch(p -> "2026-05-10".equals(p.getDataPrenotabile())));
    }

    // TC8_8_1_IT: doRetrieveByDataInizio con data futura inesistente
    @Test
    public void TC8_8_1_IT() throws SQLException {
        Collection<Prenotabile> lista = dao.doRetrieveByDataInizio("2099-12-31");
        assertTrue(lista.isEmpty());
    }

    // TC8_8_2_IT: doRetrieveByDataInizio con data presente
    @Test
    public void TC8_8_2_IT() throws SQLException {
        Collection<Prenotabile> lista = dao.doRetrieveByDataInizio("2026-05-10");
        assertFalse(lista.isEmpty());
    }

    // TC8_11_1_IT: doRetrieveByDataFine con data passata inesistente
    @Test
    public void TC8_11_1_IT() throws SQLException {
        Collection<Prenotabile> lista = dao.doRetrieveByDataFine("1999-01-01");
        assertTrue(lista.isEmpty());
    }

    // TC8_11_2_IT: doRetrieveByDataFine con data presente
    @Test
    public void TC8_11_2_IT() throws SQLException {
        Collection<Prenotabile> lista = dao.doRetrieveByDataFine("2026-05-10");
        assertFalse(lista.isEmpty());
    }
}
