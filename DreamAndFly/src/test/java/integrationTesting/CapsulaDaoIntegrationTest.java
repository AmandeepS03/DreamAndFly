package integrationTesting;

import org.junit.jupiter.api.*;
import storage.Capsula;
import storage.CapsulaDao;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.sql.*;

public class CapsulaDaoIntegrationTest {

    private static DataSource dataSource;
    private CapsulaDao dao;
    private static ConnectionTest connessionetest = new ConnectionTest();

    @BeforeAll
    public static void initDataSource() {
        connessionetest.connessione();
        dataSource = connessionetest.getDataSource();
    }

    @BeforeEach
    public void setup() {
        dao = new CapsulaDao(dataSource);
    }

    // TC6_1_1_IT: doRetrieveByKey con ID non esistente
    @Test
    public void TC6_1_1_IT() throws SQLException {
        Capsula c = dao.doRetrieveByKey(999);
        assertEquals(null, c.getId()); // ritorna oggetto con ID 0 se non trovato
    }

    // TC6_1_2_IT: doRetrieveByKey con ID esistente
    @Test
    public void TC6_1_2_IT() throws SQLException {
        Capsula c = dao.doRetrieveByKey(1);
        assertEquals(1, c.getId());
        assertEquals(9.0f, c.getPrezzo_orario());
        assertEquals("Deluxe", c.getTipologia());
    }

    // TC6_2_1_IT: doUpdatePrezzoOrario su capsula non esistente
    @Test
    public void TC6_2_1_IT() throws SQLException {
        dao.doUpdatePrezzoOrario(999, 15.0f); // non lancia eccezione, ma non fa nulla
        Capsula c = dao.doRetrieveByKey(999);
        assertEquals(null, c.getId());
    }

    // TC6_2_2_IT: doUpdatePrezzoOrario su capsula esistente
    @Test
    public void TC6_2_2_IT() throws SQLException {
        dao.doUpdatePrezzoOrario(2, 12.5f);
        Capsula c = dao.doRetrieveByKey(2);
        assertEquals(12.5f, c.getPrezzo_orario());
        assertEquals("Standard", c.getTipologia());
    }

    // TC6_3_1_IT: doSave con nuova capsula
    @Test
    public void TC6_3_1_IT() throws SQLException {
        Capsula nuova = new Capsula();
        nuova.setId(999);
        nuova.setPrezzo_orario(20.0f);
        nuova.setTipologia("Deluxe");

        dao.doSave(nuova);
        Capsula retrieved = dao.doRetrieveByKey(999);
        assertEquals(999, retrieved.getId());
        assertEquals(20.0f, retrieved.getPrezzo_orario());
        assertEquals("Deluxe", retrieved.getTipologia());

        // cleanup
        Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement("DELETE FROM capsula WHERE id = ?");
        pst.setInt(1, 999);
        pst.executeUpdate();
        pst.close();
        con.close();
    }
    
    // TC6_3_2_IT: doSave con capsula con ID già esistente
    @Test
    public void TC6_3_2_IT() {
        Capsula duplicata = new Capsula();
        duplicata.setId(1); // ID già esistente
        duplicata.setPrezzo_orario(15.0f);
        duplicata.setTipologia("Standard");

        assertThrows(SQLException.class, () -> dao.doSave(duplicata));
    }


   
}
