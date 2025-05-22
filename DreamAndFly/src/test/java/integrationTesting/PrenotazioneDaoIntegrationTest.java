package integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import com.mysql.cj.jdbc.MysqlDataSource;

import storage.Prenotazione;
import storage.PrenotazioneDao;

import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrenotazioneDaoIntegrationTest {

    private static DataSource ds;
    private PrenotazioneDao dao;
    private static int generatedId;

    @BeforeAll
    public static void initDataSource() throws SQLException {
        // Configura DataSource per MySQL
        MysqlDataSource mysqlDs = new MysqlDataSource();
        mysqlDs.setURL("jdbc:mysql://localhost:3306/dreamandfly_test?serverTimezone=UTC");
        mysqlDs.setUser("root");
        mysqlDs.setPassword("Amandeep");
        ds = mysqlDs;

        // Pulisce solo i record creati dai test precedenti (opzionale)
        try (Connection con = ds.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DELETE FROM prenotazione WHERE user_account_email = 'test.utente@dummy.com'");
        }
    }

    @BeforeEach
    public void setUp() {
        dao = new PrenotazioneDao(ds);
    }

    @Test
    @Order(1)
    public void testDoRetrieveByKey_Existing() throws SQLException {
        // Il dump contiene codice_di_accesso = 1
        Prenotazione p = dao.doRetrieveByKey(1);
        assertNotNull(p, "Prenotazione con ID=1 deve esistere");
        assertEquals(1, p.getCodiceDiAccesso());
    }

    @Test
    @Order(2)
    public void testDoSave() throws SQLException {
        Prenotazione p = new Prenotazione();
        p.setOrarioInizio("16:00");
        p.setOrarioFine("17:00");
        p.setDataInizio("2025-06-15");
        p.setDataFine("2025-06-15");
        p.setPrezzoTotale(55.0f);
        p.setDataEffettuazione("2025-05-21");
        p.setValidita(true);
        p.setRimborso(0.0f);
        p.setUserAccountEmail("mario.rossi@gmail.com");  // deve esistere
        p.setCapsulaId(1);                               // deve esistere

        // Salva e ottiene l'ID generato
        generatedId = dao.doSave(p);
        assertTrue(generatedId > 0, "L'ID generato deve essere > 0");

        // Verifica che sia recuperabile
        Prenotazione saved = dao.doRetrieveByKey(generatedId);
        assertNotNull(saved);
        assertEquals("16:00", saved.getOrarioInizio());
        assertEquals(55.0f, saved.getPrezzoTotale());
    }

    @Test
    @Order(3)
    public void testDoUpdateValidita() throws SQLException {
        // Disattiva la prenotazione appena creata
        dao.doUpdateValidita(generatedId, false);
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertFalse(p.isValidita(), "La validita deve essere false dopo l'update");
    }

    @Test
    @Order(4)
    public void testDoUpdateRimborso() throws SQLException {
        // Imposta un rimborso
        dao.doUpdateRimborso(generatedId, 12.5f);
        Prenotazione p = dao.doRetrieveByKey(generatedId);
        assertEquals(12.5f, p.getRimborso(), 0.001, "Rimborso aggiornato correttamente");
    }

    

    @Test
    @Order(5)
    public void testDoRetrievePrenotazioniByAccount() throws SQLException {
        Collection<Prenotazione> list = dao.doRetrievePrenotazioniByAccount("mario.rossi@gmail.com");
        assertNotNull(list);
        assertFalse(list.isEmpty(), "Deve tornare almeno le prenotazioni di Mario Rossi");
        // Controlla che tra queste ci sia quella appena creata
        assertTrue(list.stream().anyMatch(pr -> pr.getCodiceDiAccesso() == generatedId));
    }
}
